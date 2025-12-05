package com.hospital.service;

import com.hospital.entity.Doctor;

import java.util.List;

/**
 * 医生服务接口
 */
public interface DoctorService {
    /**
     * 根据ID查询医生
     * 
     * @param id 医生ID
     * @return 医生信息
     */
    Doctor findById(Integer id);

    /**
     * 查询所有医生
     * 
     * @return 医生列表
     */
    List<Doctor> findAll();

    /**
     * 根据科室ID查询医生
     * 
     * @param deptId 科室ID
     * @return 医生列表
     */
    List<Doctor> findByDeptId(Integer deptId);
    
    /**
     * 分页查询所有医生
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 医生列表
     */
    List<Doctor> findAllByPage(int pageNum, int pageSize);
    
    /**
     * 根据科室ID分页查询医生
     * 
     * @param deptId 科室ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 医生列表
     */
    List<Doctor> findByDeptIdAndPage(Integer deptId, int pageNum, int pageSize);
    
    /**
     * 获取医生总数
     * 
     * @return 医生总数
     */
    int getTotalCount();
    
    /**
     * 根据科室ID获取医生总数
     * 
     * @param deptId 科室ID
     * @return 医生总数
     */
    int getTotalCountByDeptId(Integer deptId);

    /**
     * 保存医生信息
     * 
     * @param doctor 医生信息
     */
    void save(Doctor doctor);

    /**
     * 更新医生信息
     * 
     * @param doctor 医生信息
     */
    void update(Doctor doctor);

    /**
     * 删除医生
     * 
     * @param id 医生ID
     */
    void delete(Integer id);
}