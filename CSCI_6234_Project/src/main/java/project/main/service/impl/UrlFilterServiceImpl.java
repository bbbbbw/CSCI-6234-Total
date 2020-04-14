package project.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.dao.UrlFilterDao;
import project.main.model.UrlFilter;
import project.main.service.UrlFilterService;

import java.util.List;

@Service("urlFilterService")
public class UrlFilterServiceImpl implements UrlFilterService {
    @Autowired
    UrlFilterDao urlFilterDao;

    @Transactional(readOnly = true)
    @Override
    public List<UrlFilter> getFilter() {
        return urlFilterDao.getFilter();
    }
}
