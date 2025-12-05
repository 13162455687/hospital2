package com.hospital.dao;

import com.hospital.entity.Department;

import java.util.List;

/**
 * 科室DAO接口
 */
public interface DepartmentDAO {
    /**
     * 根据ID查询科室
     * 
     * @param id 科室ID
     * @return 科室信息
     */
    Department findById(Integer id);

    /**
     * 查询所有科室
     * 
     * @return 科室列表
     */
    List<Department> findAll();

    /**
     * 保存科室信息
     * 
     * @param department 科室信息
     */
    void save(Department department);

    /**
     * 更新科室信息
     * 
     * @param department 科室信息
     */
    void update(Department department);

    /**
     * 根据ID删除科室
     * 
     * @param id 科室ID
     */
    void delete(Integer id);
}