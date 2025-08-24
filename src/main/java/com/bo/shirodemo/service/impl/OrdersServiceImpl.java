package com.bo.shirodemo.service.impl;

import com.bo.shirodemo.dto.OrderStatDTO;
import com.bo.shirodemo.dto.OrdersDto;
import com.bo.shirodemo.entity.Device;
import com.bo.shirodemo.entity.DeviceType;
import com.bo.shirodemo.entity.Orders;
import com.bo.shirodemo.entity.User;
import com.bo.shirodemo.repository.OrdersRepository;
import com.bo.shirodemo.service.DeviceService;
import com.bo.shirodemo.service.DeviceTypeService;
import com.bo.shirodemo.service.OrdersService;
import com.bo.shirodemo.service.UserService;
import com.bo.shirodemo.utils.Ognl;
import com.bo.shirodemo.utils.Result;
import com.bo.shirodemo.utils.ReturnResult;
import com.bo.shirodemo.utils.UUIDUtils;
import com.bo.shirodemo.vo.OrdersVo;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 订单接口实现类
 * @Author yb zheng
 * @Date 2025/7/11 16:03
 * @Version 1.0
 */

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository repository;
    private final UserService userService;
    private final DeviceTypeService deviceTypeService;
    private final DeviceService deviceService;

    public OrdersServiceImpl(OrdersRepository repository, UserService userService,
                             DeviceTypeService deviceTypeService, DeviceService deviceService) {
        this.repository = repository;
        this.userService = userService;
        this.deviceTypeService = deviceTypeService;
        this.deviceService = deviceService;
    }

    @Override
    public Result<?> createOrders(OrdersVo vo) {
        Device device = deviceService.findByDeviceNo(vo.getDeviceNo());
        DeviceType deviceType = deviceTypeService.findByDeviceId(device.getDeviceTypeId());
        User user = userService.findByUserId(device.getUserId());

        Orders orders = new Orders();
        orders.setNo(UUIDUtils.getUUID() + System.currentTimeMillis());
        orders.setDeviceId(device.getDeviceId());
        orders.setDeviceNo(vo.getDeviceNo());
        orders.setUserId(user.getUserId());
        orders.setUserName(user.getUserName());
        orders.setDeviceTypeId(deviceType.getDeviceTypeId());
        orders.setDeviceTypeName(deviceType.getDeviceTypeName());
        orders.setAmount(vo.getAmount());
        repository.save(orders);
        log.info("【订单】订单创建成功，orders = {}", orders);
        return ReturnResult.success("订单创建成功");
    }

    @Override
    public Page<OrdersDto> queryOrdersList(Integer page, Integer limit, OrdersVo vo) {
        Specification<Orders> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Ognl.isNotEmpty(vo.getUserName())) {
                predicates.add(criteriaBuilder.equal(root.get("userName"), vo.getUserName()));
            }
            if (Ognl.isNotEmpty(vo.getNo())) {
                predicates.add(criteriaBuilder.equal(root.get("no"), vo.getNo()));
            }
            if (Ognl.isNotEmpty(vo.getDeviceNo())) {
                predicates.add(criteriaBuilder.equal(root.get("deviceNo"), vo.getDeviceNo()));
            }
            if (Ognl.isNotEmpty(vo.getDeviceTypeName())) {
                predicates.add(criteriaBuilder.equal(root.get("deviceTypeName"), vo.getDeviceTypeName()));
            }
            if (Ognl.isNotEmpty(vo.getCreateTimeStart()) && Ognl.isEmpty(vo.getCreateTimeEnd())) {
                predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), vo.getCreateTimeStart()));
            } else if (Ognl.isNotEmpty(vo.getCreateTimeEnd()) && Ognl.isEmpty(vo.getCreateTimeStart())) {
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), vo.getCreateTimeEnd()));
            } else if (Ognl.isNotEmpty(vo.getCreateTimeStart()) && Ognl.isNotEmpty(vo.getCreateTimeEnd())) {
                predicates.add(criteriaBuilder.between(root.get("createTime"), vo.getCreateTimeStart(), vo.getCreateTimeEnd()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.DESC, "createTime");
        Page<Orders> all = repository.findAll(specification, pageable);
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        for (Orders orders : all.getContent()) {
            OrdersDto dto = new OrdersDto();
            BeanUtils.copyProperties(orders, dto);
            ordersDtoList.add(dto);
        }
        return new PageImpl<>(ordersDtoList, pageable, all.getTotalElements());
    }

    @Override
    public Orders findByOrdersId(Long ordersId) {
        Optional<Orders> byId = repository.findById(ordersId);
        return byId.orElse(null);
    }

    @Override
    public List<OrderStatDTO> getDailyStats(LocalDate start, LocalDate end) {
        return repository.getDailyStats(
                start.atStartOfDay(),
                end.plusDays(1).atStartOfDay().minusSeconds(1));
    }

    @Override
    public List<OrderStatDTO> getMonthlyStats(YearMonth startMonth, YearMonth endMonth) {
        LocalDateTime start = startMonth.atDay(1).atStartOfDay();
        LocalDateTime end = endMonth.atEndOfMonth().atTime(23, 59, 59);
        return repository.getMonthlyStats(start, end);
    }

}
