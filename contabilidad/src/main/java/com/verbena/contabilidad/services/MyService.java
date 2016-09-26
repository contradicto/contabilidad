package com.verbena.contabilidad.services;

import com.verbena.contabilidad.entity.Log;
import java.util.List;

public interface MyService {

	Log addLog(Log log);

	List<Log> getLogs();

	void deleteLog(Log log);
}
