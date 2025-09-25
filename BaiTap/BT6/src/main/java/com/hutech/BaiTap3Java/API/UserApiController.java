package com.hutech.BaiTap3Java.API;

import com.hutech.BaiTap3Java.model.User;
import com.hutech.BaiTap3Java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    // API de lay danh sach tat ca user
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // API de xoa mot user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    // API de cap nhat vai tro (phan quyen)
    @PutMapping("/{id}/roles")
    public ResponseEntity<User> updateUserRoles(@PathVariable Long id, @RequestBody Map<String, List<Long>> payload) {
        List<Long> roleIds = payload.get("roleIds");
        User updatedUser = userService.updateUserRoles(id, roleIds);
        return ResponseEntity.ok(updatedUser);
    }
}
