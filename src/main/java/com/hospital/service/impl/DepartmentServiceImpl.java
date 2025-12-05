package com.hospital.service.impl;

import com.hospital.dao.DepartmentDAO;
import com.hospital.dao.impl.DepartmentDAOImpl;
import com.hospital.entity.Department;
import com.hospital.service.DepartmentService;
import com.hospital.util.LogUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 科室服务实现类
 */
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LogUtil.getLogger(DepartmentServiceImpl.class);
    private DepartmentDAO departmentDAO = new DepartmentDAOImpl();

    @Override
    public Department findById(Integer id) {
        return departmentDAO.findById(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentDAO.findAll();
    }

    @Override
    public void save(Department department) {
        // 可以添加业务逻辑验证
        departmentDAO.save(department);
        logger.info("保存科室成功：" + department.getDeptName());
    }

    @Override
    public void update(Department department) {
        // 可以添加业务逻辑验证
        departmentDAO.update(department);
        logger.info("更新科室成功：" + department.getId());
    }

    @Override
    public void delete(Integer id) {
        // 可以添加业务逻辑验证，例如检查是否有相关的医生记录
        departmentDAO.delete(id);
        logger.info("删除科室成功：" + id);
    }
}