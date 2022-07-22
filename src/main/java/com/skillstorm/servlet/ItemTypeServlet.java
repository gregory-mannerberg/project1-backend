package com.skillstorm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.skillstorm.dao.ItemTypeDao;
import com.skillstorm.model.ItemType;
import com.skillstorm.model.NotFound;
import com.skillstorm.service.UrlParserService;

@WebServlet(urlPatterns="/item-type/*")
public class ItemTypeServlet extends HttpServlet {

	private static final long serialVersionUID = 7437222124191157421L;

	ItemTypeDao itemTypeDao = new ItemTypeDao();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void init() throws ServletException {
		objectMapper.registerModule(new JSR310Module());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = UrlParserService.getId(req.getPathInfo());
		String respBody;
		if (id == 0) {
			respBody = objectMapper.writeValueAsString(itemTypeDao.findAll());
		} else {
			ItemType itemType = itemTypeDao.findById(id);
			if (itemType == null) {
				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.getWriter().write(objectMapper.writeValueAsString(new NotFound("No item type with the provided id")));
				return;
			} else {
				respBody = objectMapper.writeValueAsString(itemTypeDao.findById(id));
			}
		}
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
		ItemType itemType = objectMapper.readValue(req.getInputStream(), ItemType.class);
		itemType = itemTypeDao.save(itemType);
		String respBody = objectMapper.writeValueAsString(itemType);
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ItemType itemType = objectMapper.readValue(req.getInputStream(), ItemType.class);
		itemTypeDao.update(itemType);
		resp.setStatus(200);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int[] ids = UrlParserService.getIds(req.getPathInfo());
		if (ids == null) {
			resp.setStatus(400);
			return;
		} else {
			itemTypeDao.deleteMany(ids);
			resp.setStatus(200);
		}
	}
	
}
