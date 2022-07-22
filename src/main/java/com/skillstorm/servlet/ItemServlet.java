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
import com.skillstorm.dao.ItemDao;
import com.skillstorm.model.Item;
import com.skillstorm.model.NotFound;
import com.skillstorm.service.UrlParserService;

@WebServlet(urlPatterns="/item/*")
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 6378458862942055755L;
	
	ItemDao itemDao = new ItemDao();
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
			respBody = objectMapper.writeValueAsString(itemDao.findAll());
		} else {
			Item item = itemDao.findById(id);
			if (item == null) {
				resp.setStatus(404);
				resp.setContentType("application/json");
				resp.getWriter().write(objectMapper.writeValueAsString(new NotFound("No item type with the provided id")));
				return;
			} else {
				respBody = objectMapper.writeValueAsString(itemDao.findById(id));
			}
		}
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
		Item item = objectMapper.readValue(req.getInputStream(), Item.class);
		item = itemDao.save(item);
		String respBody = objectMapper.writeValueAsString(item);
		resp.setStatus(200);
		resp.setContentType("application/json");
		resp.getWriter().write(respBody);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Item item = objectMapper.readValue(req.getInputStream(), Item.class);
		itemDao.update(item);
		resp.setStatus(200);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int[] ids = UrlParserService.getIds(req.getPathInfo());
		if (ids == null) {
			resp.setStatus(400);
			return;
		} else {
			itemDao.deleteMany(ids);
			resp.setStatus(200);
		}
	}
	
}
