package org.rxvlvxr.tasks;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rxvlvxr.tasks.database.entity.Comment;
import org.rxvlvxr.tasks.database.entity.Role;
import org.rxvlvxr.tasks.database.entity.Task;
import org.rxvlvxr.tasks.database.entity.User;
import org.rxvlvxr.tasks.database.repository.TaskRepository;
import org.rxvlvxr.tasks.database.repository.UserRepository;
import org.rxvlvxr.tasks.dicts.PriorityDictionary;
import org.rxvlvxr.tasks.dicts.StatusDictionary;
import org.rxvlvxr.tasks.dto.AuthRequestDto;
import org.rxvlvxr.tasks.dto.TaskCreateEditDto;
import org.rxvlvxr.tasks.dto.UserCreateEditDto;
import org.rxvlvxr.tasks.integration.IntegrationTestBase;
import org.rxvlvxr.tasks.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    static UUID oneTaskId;
    static UUID twoTaskId;

    static UUID oneTaskCommentId;
    static UUID twoTaskCommentId;

    static final TaskCreateEditDto VALID_TASK_EDIT_DTO = new TaskCreateEditDto(
            "test",
            "test",
            0,
            0,
            UUID.randomUUID(),
            UUID.randomUUID(),
            "test"
    );

    @BeforeAll
    static void init(@Autowired UserRepository userRepository, @Autowired UserMapper userMapper,
                     @Autowired TaskRepository taskRepository) {
        UserCreateEditDto mockAdmin = new UserCreateEditDto(), mockUser = new UserCreateEditDto();

        mockAdmin.setRole(Role.ADMIN);
        mockAdmin.setUsername("ivan");
        mockAdmin.setPassword("123");

        User entityAdmin = userMapper.toEntity(mockAdmin);

        mockUser.setRole(Role.USER);
        mockUser.setUsername("sveta");
        mockUser.setPassword("321");

        User entityUser = userMapper.toEntity(mockUser);

        userRepository.saveAll(List.of(entityUser, entityAdmin));

        Task oneTask = Task.builder()
                .authorId(entityAdmin.getId())
                .title("test 1")
                .statusId(StatusDictionary.TESTING.getId())
                .priorityId(PriorityDictionary.LOW.getId())
                .build();

        oneTask.setComments(List.of(new Comment("oneTask commentary 1", oneTask)));

        Task twoTask = Task.builder()
                .authorId(entityAdmin.getId())
                .title("test 2")
                .statusId(StatusDictionary.IN_PROGRESS.getId())
                .priorityId(PriorityDictionary.DEFAULT.getId())
                .build();

        twoTask.setComments(List.of(new Comment("twoTask commentary 1", twoTask)));

        taskRepository.saveAll(List.of(oneTask, twoTask));

        oneTaskId = oneTask.getId();
        twoTaskId = twoTask.getId();

        oneTaskCommentId = oneTask.getComments().getFirst().getId();
        twoTaskCommentId = twoTask.getComments().getFirst().getId();
    }

    @Test
    void testValidLogin() throws Exception {
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(new AuthRequestDto("ivan", "123"))))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void testInvalidLogin() throws Exception {
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(new AuthRequestDto("ivan", "321"))))
                .andExpectAll(
                        status().isUnauthorized()
                );
    }

    @Test
    @WithMockUser
    void testCreateTaskWithValidDataAsAdmin() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .with(user("test@gmail.com").authorities(Role.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(VALID_TASK_EDIT_DTO))
                )
                .andExpectAll(status().isCreated());
    }

    @Test
    @WithMockUser
    void testCreateTaskWithInvalidDataAsAdmin() throws Exception {
        var invalidTaskEditDto = VALID_TASK_EDIT_DTO;

        final Integer statusId = VALID_TASK_EDIT_DTO.getStatusId();

        invalidTaskEditDto.setStatusId(null);

        mockMvc.perform(post("/api/v1/tasks")
                        .with(user("test@gmail.com").authorities(Role.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(VALID_TASK_EDIT_DTO))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        MockMvcResultMatchers.content().json("{\"result\":400,\"id\":null,\"description\":\"\\\"statusId must not be null\\\"\"}")
                );

        invalidTaskEditDto.setStatusId(statusId);
    }

    @Test
    @WithMockUser
    void testCreateTaskWithValidDataAsUser() throws Exception {
        mockMvc.perform(post("/api/v1/tasks")
                        .with(user("test@gmail.com").authorities(Role.USER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(VALID_TASK_EDIT_DTO))
                )
                .andExpectAll(status().isForbidden());
    }

    @Test
    @WithMockUser
    void testCannotDeleteNonExistentComment() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}/comments/{commentId}", oneTaskId, UUID.randomUUID())
                        .with(user("test@gmail.com").authorities(Role.ADMIN))
                )
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    @WithMockUser
    void testDeleteExistingComment() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}/comments/{commentId}", oneTaskId, oneTaskCommentId)
                        .with(user("test@gmail.com").authorities(Role.ADMIN))
                )
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @WithMockUser
    void testDeleteTaskAsUser() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", twoTaskId)
                        .with(user("test@gmail.com").authorities(Role.USER))
                )
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @WithMockUser
    void testDeleteTaskAsAdmin() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/{id}", twoTaskId)
                        .with(user("test@gmail.com").authorities(Role.ADMIN))
                )
                .andExpectAll(
                        status().isOk()
                );
    }
}
