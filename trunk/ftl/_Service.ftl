package ${parentPackage}.service${morePackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import ${parentPackage}.mybatis.dao${morePackage}.*;
import ${parentPackage}.mybatis.entry${morePackage}.*;
import com.leweiyou.service.util.Commons;

import java.util.List;
import java.util.Set;

/**
 * 凡是以_开头的Service类，都属于自动生成的类，不提倡在这里添加service方法，请在XXXXService类里加自定义方法。
 * @author Zhangweican
 *
 */
public abstract class _${entryName}Service{

	@Autowired
	protected ${entryName}Mapper ${entityNameLowerCase}Mapper;


	//================方法自动生成区================
    /**
     * 支持分页查询
     */
    @Cacheable(value = Commons.ID_Ehcache_Default)
	public PageInfo<${entryName}> selectByExample(${entryName}Example e,int offer, int limit) throws RuntimeException{
		PageHelper.startPage(offer, limit);
		PageInfo<${entryName}> page = new PageInfo<${entryName}>(${entityNameLowerCase}Mapper.selectByExample(e));
		return page;
	}
	
    /**
     * 查询唯一对象，如果没有记录返回Null
     */
    @Cacheable(value = Commons.ID_Ehcache_Default)
 	public ${entryName} selectOne(${entryName}Example example) throws RuntimeException{
	 	List<${entryName}> list = ${entityNameLowerCase}Mapper.selectByExample(example);
     	return (list == null || list.size() == 0) ? null : list.get(0);
 	}


    /**
     * 查询记录条数
     */
    @Cacheable(value = Commons.ID_Ehcache_Default)
	public int countByExample(${entryName}Example example) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.countByExample(example);
    }

    /**
     * 删除记录
     */
    @CacheEvict(value = Commons.ID_Ehcache_Default,allEntries = true)
	public int deleteByExample(${entryName}Example example) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.deleteByExample(example);
    }

    /**
     * 通过主键删除记录
     */
    @CacheEvict(value = Commons.ID_Ehcache_Default,allEntries = true)
	public int deleteByPrimaryKey(${primaryKeyClass} id) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除记录
     */
    @CacheEvict(value = Commons.ID_Ehcache_Default,allEntries = true)
	public void batchDeleteByExample(Set<${entryName}Example> examples) throws RuntimeException{
		for(${entryName}Example example : examples){
			${entityNameLowerCase}Mapper.deleteByExample(example);
		}
    }

    /**
     * 批量通过主键删除记录
     */
    @CacheEvict(value = Commons.ID_Ehcache_Default,allEntries = true)
	public void batchDeleteByPrimaryKey(Set<${primaryKeyClass}> ids) throws RuntimeException{
		for(${primaryKeyClass} id : ids){
			${entityNameLowerCase}Mapper.deleteByPrimaryKey(id);
		}
    }

    /**
     * 插入记录
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int insert(${entryName} record) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.insert(record);
    }

    /**
     * 可选择性插入记录信息，对部分空值字段不插入
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int insertSelective(${entryName} record) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.insertSelective(record);
    }
    
	/**
	 * 批量插入记录
	 */
	@CachePut(value = Commons.ID_Ehcache_Default)
	public void batchInsert(Set<${entryName}> records) throws RuntimeException{
		for(${entryName} record : records){
			${entityNameLowerCase}Mapper.insert(record);
		}
	}
	
	/**
	 * 批量可选择性插入记录信息，对部分空值字段不插入
	 */
	@CachePut(value = Commons.ID_Ehcache_Default)
	public void batchInsertSelective(Set<${entryName}> records) throws RuntimeException{
		for(${entryName} record : records){
			${entityNameLowerCase}Mapper.insertSelective(record);
		}
	}

	/**
	 * 批量根据主键更新记录，对部分空值字段不插入
	 */
	@CachePut(value = Commons.ID_Ehcache_Default)
	public void batchUpdateByPrimaryKeySelective(Set<${entryName}> records) throws RuntimeException{
		for(${entryName} record : records){
			${entityNameLowerCase}Mapper.updateByPrimaryKeySelective(record);
		}
	}
    /**
     * 批量通过主键更新记录
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public void batchUpdateByPrimaryKey(Set<${entryName}> records) throws RuntimeException{
		for(${entryName} record : records){
			${entityNameLowerCase}Mapper.updateByPrimaryKey(record);
		}
    }

    /**
     * 查询记录
     */
    @Cacheable(value = Commons.ID_Ehcache_Default)
	public List<${entryName}> selectByExample(${entryName}Example example) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.selectByExample(example);
    }

    /**
     * 通过主键查询记录
     */
    @Cacheable(value = Commons.ID_Ehcache_Default)
	public ${entryName} selectByPrimaryKey(${primaryKeyClass} id) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件更新记录，对部分空值字段不插入
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int updateByExampleSelective(${entryName} record, ${entryName}Example example) throws RuntimeException{
         return ${entityNameLowerCase}Mapper.updateByExampleSelective(record, example);
    }

    /**
     * 根据条件更新记录
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int updateByExample(${entryName} record, ${entryName}Example example) throws RuntimeException{
         return ${entityNameLowerCase}Mapper.updateByExample(record, example);
    }

    /**
     * 根据主键更新记录，对部分空值字段不插入
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int updateByPrimaryKeySelective(${entryName} record) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 通过主键更新记录
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int updateByPrimaryKey(${entryName} record) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.updateByPrimaryKey(record);
    }
    
<#if isExistBLOBs == "true">
	/**
     * 支持大字段的查询
     */
    @Cacheable(value = Commons.ID_Ehcache_Default)
	public List<${entryName}> selectByExampleWithBLOBs(${entryName}Example example) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.selectByExampleWithBLOBs(example);
    }
    
    /**
     * 根据主键更新记录，包括大字段
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int updateByPrimaryKeyWithBLOBs(${entryName} record) throws RuntimeException{
        return ${entityNameLowerCase}Mapper.updateByPrimaryKeyWithBLOBs(record);
    }
    /**
     * 根据条件更新记录
     */
    @CachePut(value = Commons.ID_Ehcache_Default)
	public int updateByExampleWithBLOBs(${entryName} record, ${entryName}Example example) throws RuntimeException{
         return ${entityNameLowerCase}Mapper.updateByExampleWithBLOBs(record, example);
    }
</#if>    
}
