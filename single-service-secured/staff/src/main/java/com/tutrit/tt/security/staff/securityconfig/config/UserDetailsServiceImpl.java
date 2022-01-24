package com.tutrit.tt.security.staff.securityconfig.config;

import com.tutrit.tt.security.staff.securityconfig.user.Role;
import com.tutrit.tt.security.staff.securityconfig.user.RoleType;
import com.tutrit.tt.security.staff.securityconfig.user.User;
import com.tutrit.tt.security.staff.securityconfig.user.UserDao;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) {
        User user = userDao.findById(userName).orElseThrow(() -> new BadCredentialsException("неправильные имя пользователя или парол")); //validate is it ok to create default user if user not found
        return buildUserDetailsForAuthentication(user, makeUserAuthority(user));
    }

    private List<GrantedAuthority> makeUserAuthority(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }

    private UserDetails buildUserDetailsForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

    public static User getCurrentUser() {
        try {
            org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Set<Role> roles = currentUser.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> new Role(RoleType.valueOf(grantedAuthority.getAuthority())))
                    .collect(Collectors.toSet());
            return new User(currentUser.getUsername(), null, null, roles);
        } catch (Exception e) {
            return new User("UserAnonymous", null, null, null);
        }
    }

    public String getFullUserName() {
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userO = userDao.findById(currentUser.getUsername());
        if (userO.isPresent()) {
            return userO.get().getFullName();
        }
        return "";
    }

    public static boolean isUserLoggedIn() {
        if ("UserAnonymous".equals(getCurrentUser().getUsername())) {
            return false;
        }
        return true;
    }

    public void registerUser(String username, String password, String retypePassword) {
        if (!password.equals(retypePassword)) {
            throw new RuntimeException();
        }
        var user = new User(username, bCryptPasswordEncoder.encode(password), null, Set.of(new Role(RoleType.ROLE_GUEST)));
        try {
            userDao.save(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
