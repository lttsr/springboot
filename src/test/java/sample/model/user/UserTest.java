package sample.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sample.context.orm.OrmRepository;
import sample.model.master.User;
import sample.model.master.User.RegisterUser;

@Transactional
@SpringBootTest
class UserTest {

    @Autowired
    private OrmRepository rep;
    @PersistenceContext
    private EntityManager em;

    @Test
    void EntityManagerTest() {
        em.clear();
        User user = em.find(User.class, "jiro_yamada");
        assertNotNull(user);
        assertEquals("山田 次郎", user.getName());
        user = em.find(User.class, "system");
        assertNull(user);
    }

    @Test
    void registerUserTest() {
        String testId = "user1";
        // テストデータ作成
        RegisterUser param = RegisterUser.builder()
                .userId(testId)
                .groupId("groupA")
                .name("mario")
                .nameKana("マリオ")
                .mailAddress("mario@nitendo.co.jp")
                .profile("人気")
                .description("弟がいる")
                .build();
        User registeredUser = User.register(rep, param);

        // 結果検証
        assertNotNull(registeredUser);
        assertEquals("user1", registeredUser.getUserId());
        assertEquals("mario", registeredUser.getName());

        // DBの存在検証
        var user = rep.get(User.class, testId);
        assertTrue(user.isPresent());
    }
}
