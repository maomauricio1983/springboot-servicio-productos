package com.formacionbdi.springboot.app.productos.controllers;


import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {


    @Autowired
    private Environment env;

    @Autowired
    private IProductoService productoService;



    @GetMapping("/listar")
    public List<Producto> listar(){
        return  productoService.findAll().stream().map(producto -> {
            producto.setPort(Integer.valueOf(env.getProperty("local.server.port"))); //asignamos el puerto al objeto devuelto para poder verlo en la respuesta
            return producto;
        }).collect(Collectors.toList());
    }


    @GetMapping("/ver/{id}")
    public Producto ver(@PathVariable Long id){
        Producto producto = productoService.findById(id);
        producto.setPort(Integer.valueOf(env.getProperty("local.server.port"))); // obtenemos el puerto que selecciona ribbon
        return producto;
    }

}
