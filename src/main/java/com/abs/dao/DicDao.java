package com.abs.dao;



import com.abs.bean.Dic;

import java.util.List;

public interface DicDao {
    List<Dic> select(Dic dic);
}