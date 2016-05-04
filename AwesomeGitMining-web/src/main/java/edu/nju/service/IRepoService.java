package edu.nju.service;

import edu.nju.model.Pager;
import edu.nju.model.Repository;

import java.util.List;

/**
 * Created by Dora on 2016/4/30.
 */
public interface IRepoService {
    /**
     * 分页得到所有的repository(默认排序)
     * @return
     */
    Pager<Repository> getAllRepos();


    /**
     * 根据全名得到唯一的repository
     * @param fullName
     * @return
     */
    Repository getRepoByFullname(String fullName);

    /**
     * 模糊搜索
     * @param name
     * @return
     */
    Pager<Repository> searchRepository(String name);

    /**
     * 按fork排序
     * @return
     */
    Pager<Repository> getReposSortedByFork();
}
