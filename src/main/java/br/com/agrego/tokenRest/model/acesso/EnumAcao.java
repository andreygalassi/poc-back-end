package br.com.agrego.tokenRest.model.acesso;

import br.com.agrego.tokenRest.model.Colaborador;

public enum EnumAcao {
	CREATE, READ, UPDATE, DELETE;
	
	public String getAutorizacao(final Class<?> clazz){
		
		String retorno = this.name() + "_"; 

		if (clazz!=null)
			retorno += clazz.getSimpleName().toUpperCase();
		
		return retorno;
	}

	public static String DELETAR(Class<Colaborador> class1) {
		// TODO Auto-generated method stub
		return null;
	}

}
