package fr.dashingames.ludicode_android.utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Classe permettant l'execution de code javascript
 */
public class JavaScriptParser {
	private Scriptable scope;
	
	public JavaScriptParser() {
		// Initialisation des objets standards dans le js (Object, Function...)
		this.scope = getContext().initStandardObjects();
		
		Context.exit();
	}
	
	private Context getContext() {
		// On crée le contexte
		Context context = Context.enter();
		
		// On désactive toutes les optimisations pour etre compatible avec 
		// la JVM d'android (qui n'utilise pas le meme bytecode que la JVM classique)
		context.setOptimizationLevel(-1);
		
		return context;
	}
	
	/**
	 * Execute du code javascript
	 * @param code Le code javascript a executer 
	 * @return La valeur retourné par le code javascript
	 */
	public Object eval(String code) {
		Context context = getContext();
		Object result = context.evaluateString(scope, code, "<eval>", 1, null);
		Context.exit();
		return result;
	}
	
	/**
	 * Execute une fonction javascript
	 * @param functionName Nom de la fonction à executer
	 * @param arguments Les paramètres a passer à la fonction
	 * @return Le resultat de la fonction (ou null, si la fonction n'a pas été trouvé)
	 */
	public Object call(String functionName, Object[] arguments) {
		Context context = getContext();
				
		Object function = scope.get(functionName, scope);
		
		if (!(function instanceof Function))
			return null;
		
		Object result = ((Function) function).call(context, scope, scope, arguments);
		
		Context.exit();
		return result;
	}
	
	/**
	 * Execute une fonction dans une "classe" javascript
	 * @param className Nom de la classe
	 * @param functionName Nom de la fonction
	 * @param arguments Les paramètres a passer à la fonction
	 * @return Le resultat de la fonction (ou null, si la fonction n'a pas été trouvé)
	 */
	public Object call(String className, String functionName, Object[] arguments) {
		Context context = getContext();
		
		Scriptable thisObj = (Scriptable) scope.get(className, scope);
		Object function = thisObj.get(functionName, thisObj);
		
		if (!(function instanceof Function))
			return null;
		
		Object result = ((Function) function).call(context, scope, thisObj, arguments);
		
		Context.exit();
		
		return result;
	}
	
	/**
	 * Definit une variable dans le code javascript
	 * La valeur sera converti vers l'element le plus proche en javascript
	 * @param variableName Nom de la variable a definir
	 * @param value Valeur de la variable
	 */
	public void setVariable(String variableName, Object value) {
		getContext();
		Object jsValue = Context.javaToJS(value, scope);
		ScriptableObject.putProperty(scope, variableName, jsValue);
		Context.exit();
	}
	
	/**
	 * Recupere la valeur d'une variable javascript
	 * @param variableName Nom de la variable a récuperer
	 * @return Un objet correspondant à la valeur, ou null si la variable n'existe pas
	 */
	public Object getVariable(String variableName) {
		getContext();
		Object value = scope.get(variableName, scope);
		
		if (value != Scriptable.NOT_FOUND)
		    return value;
		
		Context.exit();
		return null;
	}
}
