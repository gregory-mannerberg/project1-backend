package com.skillstorm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.dao.LocationDao;
import com.skillstorm.model.Location;
import com.skillstorm.model.NotFound;
import com.skillstorm.service.UrlParserService;

@WebServlet(urlPatterns="/location/*")
public class LocationServlet extends HttpServlet {

	private static final long serialVersionUID = -6540185201097826202L;
	
	LocationDao locationDao = new LocationDao();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = UrlParserService.getId(req.getPathInfo());
		String respBody;
		if (id == 0) {
			respBody = objectMapper.writeValueAsString(locationDao.findAll());
		} else {
			Location location = locationDao.findById(id);
			if (location == null) {
				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.getWriter().write(objectMapper.writeValueAsString(new NotFound("No location with the provided id")));
				return;
			} else {
				respBody = objectMapper.writeValueAsString(locationDao.findById(id));
			}
		}
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
		Location location = objectMapper.readValue(req.getInputStream(), Location.class);
		location = locationDao.save(location);
		String respBody = objectMapper.writeValueAsString(location);
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Location location = objectMapper.readValue(req.getInputStream(), Location.class);
		locationDao.update(location);
		resp.setStatus(200);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int[] ids = UrlParserService.getIds(req.getPathInfo());
		if (ids == null) {
			resp.setStatus(400);
			return;
		} else {
			locationDao.deleteMany(ids);
			resp.setStatus(200);
		}
	}

}
