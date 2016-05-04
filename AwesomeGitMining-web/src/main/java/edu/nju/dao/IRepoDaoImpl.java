package edu.nju.dao;

import edu.nju.model.Pager;
import edu.nju.model.Repository;
import edu.nju.model.SystemContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tj on 2016/5/3.
 */
@Service("repoDao")
public class IRepoDaoImpl implements IRepoDao {
    @Resource
    private RepositoryMapper mapper;

    @Override
    public Repository selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Repository> getAll() {
        return mapper.selectAll();
    }

    @Override
    public Pager<Repository> getAllPaged() {
        Map<String, Object> map = createMap();
        List<Repository> data = mapper.selectAllPaged(map);
        return createPage(data,map);
    }


    @Override
    public Repository getReposByFullName(String full_name) {
        return mapper.selectByFullName(full_name);
    }

    @Override
    public Pager<Repository> searchRepository(String name) {
        Map<String, Object> map = createMap();
        map.put("fullName",name);
        List<Repository> data = mapper.searchRepository(map);
        Pager<Repository> page = createPage(data,map);
        page.setTotal(mapper.countSearch(name));
        return page;
    }

    @Override
    public Pager<Repository> getReposSortedByFork() {

        return null;
    }

    @Override
    public Pager<Repository> getReposSortedByContribute() {
        return null;
    }

    @Override
    public Pager<Repository> getReposSortedByStar() {
        return null;
    }

    @Override
    public Map<String, Integer> getLanguageStatistics() {
        return null;
    }

    @Override
    public Map<String, Integer> getCreatedTimeStatistics() {
        return null;
    }

    @Override
    public Map<String, Integer> getCodeFrequency(String name) {
        return null;
    }

    @Override
    public List<Repository> getReposByYear(int i) {
        return null;
    }

    private Map<String, Object> createMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        int pageSize = SystemContext.getSize();
        int pageOffset = SystemContext.getOffset();
        map.put("pageSize", pageSize);
        map.put("pageOffset", pageOffset);
        return map;
    }

    private Pager<Repository> createPage(List<Repository> data, Map<String, Object> map) {
        Pager<Repository> pages = new Pager<Repository>(data, (int)map.get("pageOffset"),
                (int)map.get("pageSize"), mapper.countAll());
        return pages;
    }
}
