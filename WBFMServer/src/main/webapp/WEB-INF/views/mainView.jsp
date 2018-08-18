<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        
		<c:url var="root"  value="/" />
		
		<link rel="stylesheet" href="${root}${themeUrl}"  type="text/css" charset="utf-8">
		
		<script src="<c:url value="/resources/js/webix/webix.js"/>" type="text/javascript" charset="utf-8"></script>
		
		<style>
			
		</style>
		
		
    </head>
    <body>
        <script type="text/javascript" charset="utf-8">
            webix.ready(function() {
			
				/* 
				   Latest Webix v5.3 uses Font Awesome v4.7.0
				   https://fontawesome.com/icons?d=gallery 
				*/
				webix.ui({
					rows: [
						{
							view: "template", template: '<spring:message code="mainView.title"/>', type:"header"
						},
						{ 
							view:"toolbar", id:"toolbar", height: 70, elements:[
								{
									view: "combo", id: "themeCombo", width: 300, label: '<spring:message code="mainView.toolbar.theme"/>',
									value: '${currentTheme}', labelWidth: 50,
									options: [ "AIR", "AIRCOMPACT", "CLOUDS", "COMPACT", "CONTRAST", "FLAT", "GLAMOUR", "LIGHT", "METRO", "TERRACE", "TOUCH", "WEB", "WEBIX" ],
									on: {
										onChange: function(newTheme, oldTheme){
											webix.send( '<c:url value="/"/>'+newTheme);
										}
									}
								},
								{},
								{
									view:"button", type:"iconButtonTop", icon:"minus-circle", 
									click: function() { window.location.href =this.config.href; }, 
									href: '<c:url value="/logout"/>',
									id: "logoutBt", label:'<spring:message code="mainView.toolbar.logout"/>', width: 110
								}
							]
						}
					]
				});
			});
		</script>
	</body>
</html>
