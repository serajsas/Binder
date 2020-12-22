package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private User user;

    @BeforeEach
    public void run() {
        user = new User(new UserInformation("Seraj Abo Sabbah", "1234"));
    }

    @Test
    void testConstructor() {
        UserInformation userInformation = user.getUserInformation();
        UserInformation userInfo = new UserInformation("Seraj Abo Sabbah", "1234");
        assertEquals(userInfo, userInformation);
        assertEquals(user, new User(userInfo));
    }

    @Test
    void testPassNoLikedUsers() {
        User userPass = new User(new UserInformation("Kevin", "123"));
        User userPassOne = new User(new UserInformation("Seraj", "123"));
        user.pass(userPass);
        user.pass(userPassOne);
        for (UserType user : user) {
            assertTrue(user instanceof UserPass);
        }
    }

    @Test
    void testLikeNoPassedUsers() {
        User userLike = new User(new UserInformation("Kevin", "123"));
        User userLikeOne = new User(new UserInformation("Seraj", "123"));
        user.like(userLike);
        user.like(userLikeOne);
        for (UserType user : user) {
            assertTrue(user instanceof UserLike);
        }
    }

    @Test
    void testLikeWithPassedUsers() {
        User userLike = new User(new UserInformation("Kevin", "123"));
        User userLikeOne = new User(new UserInformation("Seraj", "123"));
        User userPass = new User(new UserInformation("Kevin", "12345"));
        User userPassOne = new User(new UserInformation("Seraj", "1234567"));
        user.like(userLike);
        user.like(userLikeOne);
        user.pass(userPass);
        user.pass(userPassOne);
        assertTrue(user.isLiked(userLike));
        assertTrue(user.isLiked(userLikeOne));
        assertTrue(user.isPassed(userPass));
        assertTrue(user.isPassed(userPassOne));
    }
}