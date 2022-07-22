package com.skillstorm.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.dao.WarehouseDao;
import com.skillstorm.model.NotFound;
import com.skillstorm.model.Warehouse;
import com.skillstorm.service.UrlParserService;

@WebServlet(urlPatterns="/warehouse/*")
public class WarehouseServlet extends HttpServlet {

	private static final long serialVersionUID = -3319757675027850488L;
	
	WarehouseDao warehouseDao = new WarehouseDao();
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = UrlParserService.getId(req.getPathInfo());
		String respBody;
		if (id == 0) {
			respBody = objectMapper.writeValueAsString(warehouseDao.findAll());
		} else {
			Warehouse warehouse = warehouseDao.findById(id);
			if (warehouse == null) {
				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.getWriter().write(objectMapper.writeValueAsString(new NotFound("No warehouse with the provided id")));
				return;
			} else {
				respBody = objectMapper.writeValueAsString(warehouseDao.findById(id));
			}
		}
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
		Warehouse warehouse = objectMapper.readValue(req.getInputStream(), Warehouse.class);
		warehouse = warehouseDao.save(warehouse);
		String respBody = objectMapper.writeValueAsString(warehouse);
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Warehouse warehouse = objectMapper.readValue(req.getInputStream(), Warehouse.class);
		warehouseDao.update(warehouse);
		resp.setStatus(200);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int[] ids = UrlParserService.getIds(req.getPathInfo());
		System.out.println(req.getPathInfo());
		System.out.println(ids);
		if (ids == null) {
			resp.setStatus(400);
			return;
		} else {
			warehouseDao.deleteMany(ids);
			resp.setStatus(200);
		}
	}
	
}
