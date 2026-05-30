package io.github.ShabdanBatyrkulov.lms.service;

import io.github.ShabdanBatyrkulov.lms.model.User;
import io.github.ShabdanBatyrkulov.lms.repository.UserRepository;
import io.github.ShabdanBatyrkulov.lms.dto.UserRegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl Tests")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegistrationDto validRegistrationDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        validRegistrationDto = new UserRegistrationDto("testuser", "password123");
        testUser = new User("testuser", "hashedPassword");
        testUser.setId(UUID.randomUUID());
    }

    // ==================== registerUser() Tests ====================

    @Test
    @DisplayName("Should successfully register a new user with valid credentials")
    void testRegisterUserSuccess() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User result = userService.registerUser(validRegistrationDto);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("hashedPassword", result.getPasswordHash());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when username already exists")
    void testRegisterUserWithDuplicateUsername() {
        // Arrange
        User existingUser = new User("testuser", "existingHash");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(validRegistrationDto)
        );

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should hash password before saving user")
    void testPasswordEncodingDuringRegistration() {
        // Arrange
        String rawPassword = "mySecurePassword";
        String encodedPassword = "encodedSecurePassword";
        validRegistrationDto.setPassword(rawPassword);

        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.registerUser(validRegistrationDto);

        // Assert
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    @DisplayName("Should save user with correct username and hashed password")
    void testUserSavedWithCorrectData() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        userService.registerUser(validRegistrationDto);

        // Assert
        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("testuser") &&
                user.getPasswordHash().equals("hashedPassword")
        ));
    }

    @Test
    @DisplayName("Should return saved user with ID")
    void testRegisterUserReturnsUserWithId() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User savedUser = new User("testuser", "hashedPassword");
        savedUser.setId(userId);

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerUser(validRegistrationDto);

        // Assert
        assertNotNull(result.getId());
        assertEquals(userId, result.getId());
    }

    @Test
    @DisplayName("Should handle special characters in username during registration")
    void testRegisterUserWithSpecialCharactersInUsername() {
        // Arrange
        UserRegistrationDto dtoWithSpecialChars = new UserRegistrationDto("user_name-123", "password123");
        User userWithSpecialChars = new User("user_name-123", "hashedPassword");

        when(userRepository.findByUsername("user_name-123")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userWithSpecialChars);

        // Act
        User result = userService.registerUser(dtoWithSpecialChars);

        // Assert
        assertEquals("user_name-123", result.getUsername());
        verify(userRepository, times(1)).findByUsername("user_name-123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should handle case-sensitive username comparison")
    void testRegisterUserWithCaseSensitiveUsername() {
        // Arrange
        UserRegistrationDto dtoLowercase = new UserRegistrationDto("testuser", "password123");
        User existingUserUppercase = new User("TESTUSER", "hashedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act - Should succeed as usernames are case-sensitive
        User result = userService.registerUser(dtoLowercase);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ==================== findByUsername() Tests ====================

    @Test
    @DisplayName("Should find user by username successfully")
    void testFindByUsernameSuccess() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);

        // Act
        User result = userService.findByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("hashedPassword", result.getPasswordHash());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should return null when user is not found")
    void testFindByUsernameNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistent")).thenReturn(null);

        // Act
        User result = userService.findByUsername("nonexistent");

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    @DisplayName("Should return correct user with all properties")
    void testFindByUsernameReturnsCompleteUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = new User("testuser", "hashedPassword");
        expectedUser.setId(userId);

        when(userRepository.findByUsername("testuser")).thenReturn(expectedUser);

        // Act
        User result = userService.findByUsername("testuser");

        // Assert
        assertEquals(userId, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("hashedPassword", result.getPasswordHash());
    }

    @Test
    @DisplayName("Should handle special characters in username during lookup")
    void testFindByUsernameWithSpecialCharacters() {
        // Arrange
        User userWithSpecialChars = new User("user_name-123", "hashedPassword");
        when(userRepository.findByUsername("user_name-123")).thenReturn(userWithSpecialChars);

        // Act
        User result = userService.findByUsername("user_name-123");

        // Assert
        assertNotNull(result);
        assertEquals("user_name-123", result.getUsername());
        verify(userRepository, times(1)).findByUsername("user_name-123");
    }

    @Test
    @DisplayName("Should handle case-sensitive username lookup")
    void testFindByUsernameWithCaseSensitivity() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(testUser);
        when(userRepository.findByUsername("TESTUSER")).thenReturn(null);

        // Act
        User resultLowercase = userService.findByUsername("testuser");
        User resultUppercase = userService.findByUsername("TESTUSER");

        // Assert
        assertNotNull(resultLowercase);
        assertNull(resultUppercase);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).findByUsername("TESTUSER");
    }
}
