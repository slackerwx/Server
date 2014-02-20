package br.com.baladasp.cgt.util;

import java.lang.reflect.Type;

import br.com.baladasp.cdp.usuario.Usuario;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UsuarioSerializer implements JsonSerializer<Usuario> {

	@Override
	public JsonElement serialize(Usuario usuario, Type arg1, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("IDTwitter", usuario.getIDTwitter());
		json.addProperty("usuario", usuario.getUsuario());
		json.addProperty("screenName", usuario.getScreenName());
		json.addProperty("urlImagemUsuario",usuario.getUrlImagem());

		return json;
	}

}
