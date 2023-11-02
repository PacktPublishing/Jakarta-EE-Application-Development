package com.ensode.jakartaeebook.singletonsession;

import java.util.List;
import com.ensode.jakartaeebook.entity.UsStates;
import jakarta.ejb.Remote;

@Remote
public interface SingletonSessionBeanRemote {

  List<UsStates> getStateList();
}
