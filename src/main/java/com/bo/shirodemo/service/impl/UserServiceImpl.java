package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.dto.UserDto;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.entity.UserRole;
import com.bo.shirodemo.repository.UserRepository;
import com.bo.shirodemo.repository.UserRoleRepository;
import com.bo.shirodemo.service.UserService;
import com.bo.shirodemo.utils.*;
import com.bo.shirodemo.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author bo
 * @DATE 2019/12/23
 **/

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository repository, UserRoleRepository userRoleRepository) {
        this.repository = repository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Page<UserDto> queryUserList(Integer page, Integer limit, UserVo vo) {
        Specification<User> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Ognl.isNotEmpty(vo.getUserName())) {
                predicates.add(criteriaBuilder.equal(root.get("userName"), vo.getUserName()));
            }
            if (Ognl.isNotEmpty(vo.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), vo.getStatus()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(page-1, limit, Sort.Direction.DESC, "userId");
        Page<User> all = repository.findAll(specification, pageable);
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : all.getContent()) {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(user, dto);
            userDtoList.add(dto);
        }
        return new PageImpl<>(userDtoList, pageable, all.getTotalElements());
    }

    @Override
    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public User findByUserId(Long userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public User getUserInfo() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (Ognl.isNotEmpty(principal)) {
            return this.findByUserName(principal.toString());
        }
        return null;
    }

    @Transient
    @Override
    public Result<?> saveUser(User user) {
        if (Ognl.isEmpty(user.getUserName())) {
            return ReturnResult.fail(400, "用户名不能为空");
        }
        User byUserName = this.findByUserName(user.getUserName());
        if (Ognl.isNotEmpty(byUserName)) {
            return ReturnResult.fail(400, "该用户已存在");
        }
        user.setStatus("1");
        user.setUserType(Constant.USER_TYPE_USER);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        User save = repository.save(user);
        // 添加用户角色
        UserRole userRole = new UserRole();
        userRole.setUserId(save.getUserId());
        userRole.setRoleId(2L);
        userRoleRepository.save(userRole);

        return ReturnResult.success("添加成功");
    }

    @Override
    public Result<?> updateUser(User user) {
        User u = findByUserId(user.getUserId());
        User byUserName = findByUserName(user.getUserName());
        if (Ognl.isNotEmpty(byUserName)) {
            return ReturnResult.fail(400, "用户名已存在");
        }
        if (Ognl.isNotEmpty(user.getPassword())) {
            u.setPassword(Md5Util.pwdEncr(user.getPassword()));
        }
        if (Ognl.isNotEmpty(user.getStatus())) {
            u.setStatus(user.getStatus());
        }
        if (Ognl.isNotEmpty(user.getRemark())) {
            if (Constant.NULL_VALUE.equals(user.getRemark())) {
                u.setRemark(null);
            } else {
                u.setRemark(user.getRemark());
            }
        }
        repository.save(u);
        return ReturnResult.success("修改成功");
    }

    @Override
    public Result<?> bandUser(User user) {
        User u = findByUserId(user.getUserId());
        if (u.getStatus().equals("1")) {
            u.setStatus("2");
        } else {
            u.setStatus("1");
        }
        repository.save(u);
        return ReturnResult.success("修改成功");
    }

    @Override
    public List<UserDto> findAll() {
        List<User> all = repository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : all) {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(user, dto);
            userDtoList.add(dto);
        }
        return userDtoList;
    }

}
