package com.tool

import com.tool.Role
import com.tool.User
import com.tool.UserRole

class BootStrap {

    def init = { servletContext ->
        new Role(authority: 'ROLE_ADMIN').save()
        new Role(authority: 'ROLE_USER').save()
        Product p = new Product(prodCode: 'P0001', prodName: 'iPhone', prodModel: 'XS', prodDesc: 'desc', prodImageUrl: 'https://ibox.co.id/media/catalog/product/cache/3/image/9df78eab33525d08d6e5fb8d27136e95/i/p/iphonexs-max-gold_3_1.png', prodPrice: 999)
		p.save()
		Product b = Product.findByProdName('iPhone')
		String s = "asdf"
    }
    def destroy = {
    }
}
