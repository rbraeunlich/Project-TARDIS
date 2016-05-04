package kr.ac.kaist.se.tardis.project.impl;

import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.project.api.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	public String foo() {
		return "World";
	}

}
