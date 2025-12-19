/*
 * Copyright 2020-2099 sa-token.cc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peng.sms.util;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.fun.SaFunction;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.listener.SaTokenEventCenter;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.TokenSign;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;

import java.util.List;

/**
 * @auther macrozheng
 * @description Frontend mall user login authentication utility class
 * Copied from the default StpUtil, only changed the TYPE
 * @date 2024/1/26
 * @github https://github.com/macrozheng
 */
public class StpMemberUtil {

	private StpMemberUtil() {}
	
	/**
	 * Type identifier for multi-account system
	 */
	public static final String TYPE = "memberLogin";
	
	/**
	 * Underlying StpLogic object used
	 */
	public static StpLogic stpLogic = new StpLogicJwtForSimple(TYPE);

	/**
	 * Get the account type of the current StpLogic
	 *
	 * @return /
	 */
	public static String getLoginType(){
		return stpLogic.getLoginType();
	}

	/**
	 * Safely reset the StpLogic object
	 *
	 * <br> 1. Change the StpLogic object of this account 
	 * <br> 2. Put it into the global StpLogic collection 
	 * <br> 3. Send log 
	 * 
	 * @param newStpLogic / 
	 */
	public static void setStpLogic(StpLogic newStpLogic) {
		// 1. Reset the StpLogic object of this account
		stpLogic = newStpLogic;
		
		// 2. Add to the global StpLogic collection
		//    So that this StpLogic can be globally obtained through SaManager.getStpLogic(type)
		SaManager.putStpLogic(newStpLogic);
		
		// 3. $$ Publish event: updated stpLogic object
		SaTokenEventCenter.doSetStpLogic(stpLogic);
	}

	/**
	 * Get the StpLogic object
	 *
	 * @return / 
	 */
	public static StpLogic getStpLogic() {
		return stpLogic;
	}
	
	
	// ------------------- Get token related -------------------

	/**
	 * Return the token name, this name is reflected in the following places: the name when Cookie saves the token, the parameter name when submitting the token, and the key prefix when storing the token
	 *
	 * @return /
	 */
	public static String getTokenName() {
 		return stpLogic.getTokenName();
 	}

	/**
	 * Write the specified token value in the current session
	 *
	 * @param tokenValue token value
	 */
	public static void setTokenValue(String tokenValue){
		stpLogic.setTokenValue(tokenValue);
	}

	/**
	 * Write the specified token value in the current session
	 *
	 * @param tokenValue token value
	 * @param cookieTimeout Cookie lifetime (seconds)
	 */
	public static void setTokenValue(String tokenValue, int cookieTimeout){
		stpLogic.setTokenValue(tokenValue, cookieTimeout);
	}

	/**
	 * Write the specified token value in the current session
	 *
	 * @param tokenValue token value
	 * @param loginModel login parameters
	 */
	public static void setTokenValue(String tokenValue, SaLoginModel loginModel){
		stpLogic.setTokenValue(tokenValue, loginModel);
	}

	/**
	 * Get the token value of the current request
	 *
	 * @return current tokenValue
	 */
	public static String getTokenValue() {
		return stpLogic.getTokenValue();
	}

	/**
	 * Get the token value of the current request (without trimming the prefix)
	 *
	 * @return / 
	 */
	public static String getTokenValueNotCut(){
		return stpLogic.getTokenValueNotCut();
	}

	/**
	 * Get the token parameter information of the current session
	 *
	 * @return token parameter information
	 */
	public static SaTokenInfo getTokenInfo() {
		return stpLogic.getTokenInfo();
	}

	
	// ------------------- Login related operations -------------------

	// --- Login 

	/**
	 * Session login
	 *
	 * @param id account id, suggested types: (long | int | String)
	 */
	public static void login(Object id) {
		stpLogic.login(id);
	}

	/**
	 * Session login, and specify the login device type
	 *
	 * @param id account id, suggested types: (long | int | String)
	 * @param device device type
	 */
	public static void login(Object id, String device) {
		stpLogic.login(id, device);
	}

	/**
	 * Session login, and specify whether to [remember me]
	 *
	 * @param id account id, suggested types: (long | int | String)
	 * @param isLastingCookie whether it is a persistent Cookie, when true remember me, when false need to login again after closing browser
	 */
	public static void login(Object id, boolean isLastingCookie) {
		stpLogic.login(id, isLastingCookie);
	}

	/**
	 * Session login, and specify the validity period of this login token, unit: seconds
	 *
	 * @param id      account id, suggested types: (long | int | String)
	 * @param timeout validity period of this login token, unit: seconds
	 */
	public static void login(Object id, long timeout) {
		stpLogic.login(id, timeout);
	}

	/**
	 * Session login, and specify all login parameter Model
	 *
	 * @param id account id, suggested types: (long | int | String)
	 * @param loginModel parameter Model for this login
	 */
	public static void login(Object id, SaLoginModel loginModel) {
		stpLogic.login(id, loginModel);
	}

	/**
	 * Create login session data for the specified account id
	 *
	 * @param id account id, suggested types: (long | int | String)
	 * @return return session token
	 */
	public static String createLoginSession(Object id) {
		return stpLogic.createLoginSession(id);
	}

	/**
	 * Create login session data for the specified account id
	 *
	 * @param id account id, suggested types: (long | int | String)
	 * @param loginModel parameter Model for this login 
	 * @return return session token
	 */
	public static String createLoginSession(Object id, SaLoginModel loginModel) {
		return stpLogic.createLoginSession(id, loginModel);
	}
	
	// --- Logout 

	/**
	 * Logout in the current client session
	 */
	public static void logout() {
		stpLogic.logout();
	}

	/**
	 * Session logout, by account id 
	 *
	 * @param loginId account id
	 */
	public static void logout(Object loginId) {
		stpLogic.logout(loginId);
	}

	/**
	 * Session logout, by account id and device type
	 *
	 * @param loginId account id 
	 * @param device device type (fill null to logout all device types of this account)
	 */
	public static void logout(Object loginId, String device) {
		stpLogic.logout(loginId, device);
	}

	/**
	 * Session logout, by specified Token 
	 *
	 * @param tokenValue specified token
	 */
	public static void logoutByTokenValue(String tokenValue) {
		stpLogic.logoutByTokenValue(tokenValue);
	}

	/**
	 * Kick user offline, by account id 
	 * <p> When the other party accesses the system again, a NotLoginException exception will be thrown, with scenario value=-5 </p>
	 *
	 * @param loginId account id 
	 */
	public static void kickout(Object loginId) {
		stpLogic.kickout(loginId);
	}

	/**
	 * Kick user offline, by account id and device type
	 * <p> When the other party accesses the system again, a NotLoginException exception will be thrown, with scenario value=-5 </p>
	 *
	 * @param loginId account id
	 * @param device device type (fill null to kick out all device types of this account)
	 */
	public static void kickout(Object loginId, String device) {
		stpLogic.kickout(loginId, device);
	}

	/**
	 * Kick user offline, by specified token
	 * <p> When the other party accesses the system again, a NotLoginException exception will be thrown, with scenario value=-5 </p>
	 *
	 * @param tokenValue specified token
	 */
	public static void kickoutByTokenValue(String tokenValue) {
		stpLogic.kickoutByTokenValue(tokenValue);
	}

	/**
	 * Replace user offline, by account id and device type
	 * <p> When the other party accesses the system again, a NotLoginException exception will be thrown, with scenario value=-4 </p>
	 *
	 * @param loginId account id
	 * @param device device type (fill null to replace all device types of this account)
	 */
	public static void replaced(Object loginId, String device) {
		stpLogic.replaced(loginId, device);
	}

	// Session query

	/**
	 * Determine whether the current session is logged in
	 *
	 * @return return true if logged in, return false if not logged in
	 */
	public static boolean isLogin() {
		return stpLogic.isLogin();
	}

	/**
	 * Determine whether the specified account is logged in
	 *
	 * @return return true if logged in, return false if not logged in
	 */
	public static boolean isLogin(Object loginId) {
		return stpLogic.isLogin(loginId);
	}

	/**
	 * Check whether the current session is logged in, if not logged in, throw an exception
	 */
 	public static void checkLogin() {
 		stpLogic.checkLogin();
 	}

	/**
	 * Get the current session account id, if not logged in, throw an exception
	 *
	 * @return account id
	 */
	public static Object getLoginId() {
		return stpLogic.getLoginId();
	}

	/**
	 * Get the current session account id, if not logged in, return the default value
	 *
	 * @param <T> return type 
	 * @param defaultValue default value
	 * @return login id
	 */
	public static <T> T getLoginId(T defaultValue) {
		return stpLogic.getLoginId(defaultValue);
	}

	/**
	 * Get the current session account id, if not logged in, return null
	 *
	 * @return account id
	 */
	public static Object getLoginIdDefaultNull() {
		return stpLogic.getLoginIdDefaultNull();
 	}

	/**
	 * Get the current session account id, and convert to String type
	 *
	 * @return account id
	 */
	public static String getLoginIdAsString() {
		return stpLogic.getLoginIdAsString();
	}

	/**
	 * Get the current session account id, and convert to int type
	 *
	 * @return account id
	 */
	public static int getLoginIdAsInt() {
		return stpLogic.getLoginIdAsInt();
	}

	/**
	 * Get the current session account id, and convert to long type
	 *
	 * @return account id
	 */
	public static long getLoginIdAsLong() {
		return stpLogic.getLoginIdAsLong();
	}

	/**
	 * Get the account id corresponding to the specified token, if not logged in, return null
	 *
	 * @param tokenValue token
	 * @return account id
	 */
 	public static Object getLoginIdByToken(String tokenValue) {
 		return stpLogic.getLoginIdByToken(tokenValue);
 	}

	/**
	 * Get the extended information of the current Token (this function only works in jwt mode)
	 *
	 * @param key key value 
	 * @return corresponding extended data
	 */
	public static Object getExtra(String key) {
		return stpLogic.getExtra(key);
	}

	/**
	 * Get the extended information of the specified Token (this function only works in jwt mode)
	 *
	 * @param tokenValue specified Token value
	 * @param key key value
	 * @return corresponding extended data
	 */
	public static Object getExtra(String tokenValue, String key) {
		return stpLogic.getExtra(tokenValue, key);
	}
 	
 	
	// ------------------- Account-Session related -------------------

	/**
	 * Get the Account-Session of the specified account id, if the SaSession has not been created yet, isCreate=whether to create and return
	 *
	 * @param loginId account id
	 * @param isCreate whether to create
	 * @return SaSession object
	 */
	public static SaSession getSessionByLoginId(Object loginId, boolean isCreate) {
		return stpLogic.getSessionByLoginId(loginId, isCreate);
	}

	/**
	 * Get the SaSession of the specified key, if the SaSession has not been created yet, return null
	 *
	 * @param sessionId SessionId
	 * @return Session object
	 */
	public static SaSession getSessionBySessionId(String sessionId) {
		return stpLogic.getSessionBySessionId(sessionId);
	}

	/**
	 * Get the Account-Session of the specified account id, if the SaSession has not been created yet, create and return
	 *
	 * @param loginId account id
	 * @return SaSession object
	 */
	public static SaSession getSessionByLoginId(Object loginId) {
		return stpLogic.getSessionByLoginId(loginId);
	}

	/**
	 * Get the Account-Session of the current logged-in account, if the SaSession has not been created yet, isCreate=whether to create and return
	 *
	 * @param isCreate whether to create 
	 * @return Session object
	 */
	public static SaSession getSession(boolean isCreate) {
		return stpLogic.getSession(isCreate);
	}

	/**
	 * Get the Account-Session of the current logged-in account, if the SaSession has not been created yet, create and return
	 *
	 * @return Session object
	 */
	public static SaSession getSession() {
		return stpLogic.getSession();
	}

	
	// ------------------- Token-Session related -------------------  

	/**
	 * Get the Token-Session of the specified token, if the SaSession has not been created yet, create and return
	 *
	 * @param tokenValue Token value
	 * @return Session object
	 */
	public static SaSession getTokenSessionByToken(String tokenValue) {
		return stpLogic.getTokenSessionByToken(tokenValue);
	}

	/**
	 * Get the Token-Session of the current token, if the SaSession has not been created yet, create and return
	 *
	 * @return Session object
	 */
	public static SaSession getTokenSession() {
		return stpLogic.getTokenSession();
	}

	/**
	 * Get the current anonymous Token-Session (Token-Session that can be used without logging in)
	 *
	 * @return Token-Session object
	 */
	public static SaSession getAnonTokenSession() {
		return stpLogic.getAnonTokenSession();
	}
	

	// ------------------- Active-Timeout token minimum activity verification related -------------------

	/**
	 * Renew the current token: (update the [last operation time] to the current timestamp)
	 * <h2>
	 * 		Please note: even if the token has been frozen, renewal can succeed,
	 * 		if you need to prompt renewal failure in this scenario, you can call checkActiveTimeout() before this to force check whether it is frozen
	 * </h2>
	 */
	public static void updateLastActiveToNow() {
		stpLogic.updateLastActiveToNow();
	}

	/**
	 * Check whether the current token has been frozen, if so, throw an exception
	 */
 	public static void checkActiveTimeout() {
 		stpLogic.checkActiveTimeout();
 	}


	// ------------------- Expiration time related -------------------  

	/**
	 * Get the remaining validity time of the current session token (unit: seconds, return -1 for permanent validity, -2 for no such value)
	 *
	 * @return token remaining validity time
	 */
 	public static long getTokenTimeout() {
 		return stpLogic.getTokenTimeout();
 	}

	/**
	 * Get the remaining validity time of the specified token (unit: seconds, return -1 for permanent validity, -2 for no such value)
	 *
	 * @param token specified token
	 * @return token remaining validity time
	 */
	public static long getTokenTimeout(String token) {
		return stpLogic.getTokenTimeout(token);
	}

	/**
	 * Get the remaining validity time of the current logged-in account's Account-Session (unit: seconds, return -1 for permanent validity, -2 for no such value)
	 *
	 * @return token remaining validity time
	 */
 	public static long getSessionTimeout() {
 		return stpLogic.getSessionTimeout();
 	}

	/**
	 * Get the remaining validity time of the current token's Token-Session (unit: seconds, return -1 for permanent validity, -2 for no such value)
	 *
	 * @return token remaining validity time
	 */
 	public static long getTokenSessionTimeout() {
 		return stpLogic.getTokenSessionTimeout();
 	}

	/**
	 * Get the remaining active validity period of the current token: how much time is left before the current token is frozen (unit: seconds, return -1 for never freeze, -2 for no such value or token has been frozen)
	 *
	 * @return /
	 */
 	public static long getTokenActiveTimeout() {
 		return stpLogic.getTokenActiveTimeout();
 	}

	/**
	 * Renew the timeout value of the current token
	 *
	 * @param timeout the validity time to be modified to (unit: seconds)
	 */
 	public static void renewTimeout(long timeout) {
 		stpLogic.renewTimeout(timeout);
 	}

	/**
	 * Renew the timeout value of the specified token
	 *
	 * @param tokenValue specified token
	 * @param timeout the validity time to be modified to (unit: seconds, fill -1 to renew to permanent validity)
	 */
 	public static void renewTimeout(String tokenValue, long timeout) {
 		stpLogic.renewTimeout(tokenValue, timeout);
 	}
 	
 	
	// ------------------- Role authentication operations -------------------

	/**
	 * Get: role collection of the current account
	 *
	 * @return /
	 */
	public static List<String> getRoleList() {
		return stpLogic.getRoleList();
	}

	/**
	 * Get: role collection of the specified account
	 *
	 * @param loginId specified account id 
	 * @return /
	 */
	public static List<String> getRoleList(Object loginId) {
		return stpLogic.getRoleList(loginId);
	}

	/**
	 * Determine: whether the current account has the specified role, return true or false
	 *
	 * @param role role
	 * @return /
	 */
 	public static boolean hasRole(String role) {
 		return stpLogic.hasRole(role);
 	}

	/**
	 * Determine: whether the specified account has the specified role identifier, return true or false
	 *
	 * @param loginId account id
	 * @param role role identifier
	 * @return whether it has the specified role identifier
	 */
 	public static boolean hasRole(Object loginId, String role) {
 		return stpLogic.hasRole(loginId, role);
 	}

	/**
	 * Determine: whether the current account has the specified role identifier [ specify multiple, all must be verified ]
	 *
	 * @param roleArray role identifier array
	 * @return true or false
	 */
 	public static boolean hasRoleAnd(String... roleArray){
 		return stpLogic.hasRoleAnd(roleArray);
 	}

	/**
	 * Determine: whether the current account has the specified role identifier [ specify multiple, as long as one is verified ]
	 *
	 * @param roleArray role identifier array
	 * @return true or false
	 */
 	public static boolean hasRoleOr(String... roleArray){
 		return stpLogic.hasRoleOr(roleArray);
 	}

	/**
	 * Verify: whether the current account has the specified role identifier, if verification fails, throw exception: NotRoleException
	 *
	 * @param role role identifier
	 */
 	public static void checkRole(String role) {
 		stpLogic.checkRole(role);
 	}

	/**
	 * Verify: whether the current account has the specified role identifier [ specify multiple, all must be verified ]
	 *
	 * @param roleArray role identifier array
	 */
 	public static void checkRoleAnd(String... roleArray){
 		stpLogic.checkRoleAnd(roleArray);
 	}

	/**
	 * Verify: whether the current account has the specified role identifier [ specify multiple, as long as one is verified ]
	 *
	 * @param roleArray role identifier array
	 */
 	public static void checkRoleOr(String... roleArray){
 		stpLogic.checkRoleOr(roleArray);
 	}

	
	// ------------------- Permission authentication operations -------------------

	/**
	 * Get: permission code collection of the current account
	 *
	 * @return / 
	 */
	public static List<String> getPermissionList() {
		return stpLogic.getPermissionList();
	}

	/**
	 * Get: permission code collection of the specified account
	 *
	 * @param loginId specified account id
	 * @return / 
	 */
	public static List<String> getPermissionList(Object loginId) {
		return stpLogic.getPermissionList(loginId);
	}

	/**
	 * Determine: whether the current account has the specified permission, return true or false
	 *
	 * @param permission permission code
	 * @return whether it has the specified permission
	 */
	public static boolean hasPermission(String permission) {
		return stpLogic.hasPermission(permission);
	}

	/**
	 * Determine: whether the specified account id has the specified permission, return true or false
	 *
	 * @param loginId account id
	 * @param permission permission code
	 * @return whether it has the specified permission
	 */
	public static boolean hasPermission(Object loginId, String permission) {
		return stpLogic.hasPermission(loginId, permission);
	}

	/**
	 * Determine: whether the current account has the specified permission [ specify multiple, all must be possessed ]
	 *
	 * @param permissionArray permission code array
	 * @return true or false
	 */
 	public static boolean hasPermissionAnd(String... permissionArray){
 		return stpLogic.hasPermissionAnd(permissionArray);
 	}

	/**
	 * Determine: whether the current account has the specified permission [ specify multiple, as long as one is verified ]
	 *
	 * @param permissionArray permission code array
	 * @return true or false
	 */
 	public static boolean hasPermissionOr(String... permissionArray){
 		return stpLogic.hasPermissionOr(permissionArray);
 	}

	/**
	 * Verify: whether the current account has the specified permission, if verification fails, throw exception: NotPermissionException
	 *
	 * @param permission permission code
	 */
	public static void checkPermission(String permission) {
		stpLogic.checkPermission(permission);
	}

	/**
	 * Verify: whether the current account has the specified permission [ specify multiple, all must be verified ]
	 *
	 * @param permissionArray permission code array
	 */
	public static void checkPermissionAnd(String... permissionArray) {
		stpLogic.checkPermissionAnd(permissionArray);
	}

	/**
	 * Verify: whether the current account has the specified permission [ specify multiple, as long as one is verified ]
	 *
	 * @param permissionArray permission code array
	 */
	public static void checkPermissionOr(String... permissionArray) {
		stpLogic.checkPermissionOr(permissionArray);
	}


	// ------------------- id reverse lookup token related operations -------------------

	/**
	 * Get the token of the specified account id
	 * <p>
	 * 		When configured to allow concurrent login, this method will only return the last token in the queue,
	 * 		if you need to return all tokens of this account id, please call getTokenValueListByLoginId
	 * </p>
	 *
	 * @param loginId account id
	 * @return token value
	 */
	public static String getTokenValueByLoginId(Object loginId) {
		return stpLogic.getTokenValueByLoginId(loginId);
	}

	/**
	 * Get the token of the specified account id for the specified device type
	 * <p>
	 * 		When configured to allow concurrent login, this method will only return the last token in the queue,
	 * 		if you need to return all tokens of this account id, please call getTokenValueListByLoginId
	 * </p>
	 *
	 * @param loginId account id
	 * @param device device type, fill null for no device type limit
	 * @return token value
	 */
	public static String getTokenValueByLoginId(Object loginId, String device) {
		return stpLogic.getTokenValueByLoginId(loginId, device);
	}

	/**
	 * Get the token collection of the specified account id
	 *
	 * @param loginId account id
	 * @return all related tokens of this loginId
	 */
	public static List<String> getTokenValueListByLoginId(Object loginId) {
		return stpLogic.getTokenValueListByLoginId(loginId);
	}

	/**
	 * Get the token collection of the specified account id for the specified device type
	 *
	 * @param loginId account id
	 * @param device device type, fill null for no device type limit
	 * @return all login tokens of this loginId
	 */
	public static List<String> getTokenValueListByLoginId(Object loginId, String device) {
		return stpLogic.getTokenValueListByLoginId(loginId, device);
	}

	/**
	 * Get the tokenSign collection of the specified account id for the specified device type
	 *
	 * @param loginId account id
	 * @param device device type, fill null for no device type limit
	 * @return all login tokenSign of this loginId
	 */
	public static List<TokenSign> getTokenSignListByLoginId(Object loginId, String device) {
		return stpLogic.getTokenSignListByLoginId(loginId, device);
	}

	/**
	 * Return the login device type of the current session
	 *
	 * @return the login device type of the current token
	 */
	public static String getLoginDevice() {
		return stpLogic.getLoginDevice(); 
	}

	
	// ------------------- Session management -------------------  

	/**
	 * Search all tokens in the cache based on conditions
	 *
	 * @param keyword keyword
	 * @param start start index
	 * @param size quantity to get (-1 means get to the end)
	 * @param sortType sort type (true=ascending order, false=descending order)
	 *
	 * @return token collection
	 */
	public static List<String> searchTokenValue(String keyword, int start, int size, boolean sortType) {
		return stpLogic.searchTokenValue(keyword, start, size, sortType);
	}

	/**
	 * Search all SessionIds in the cache based on conditions
	 *
	 * @param keyword keyword
	 * @param start start index
	 * @param size quantity to get  (-1 means get to the end)
	 * @param sortType sort type (true=ascending order, false=descending order)
	 *
	 * @return sessionId collection
	 */
	public static List<String> searchSessionId(String keyword, int start, int size, boolean sortType) {
		return stpLogic.searchSessionId(keyword, start, size, sortType);
	}

	/**
	 * Search all Token-Session-Ids in the cache based on conditions
	 *
	 * @param keyword keyword
	 * @param start start index
	 * @param size quantity to get (-1 means get to the end)
	 * @param sortType sort type (true=ascending order, false=descending order)
	 *
	 * @return sessionId collection
	 */
	public static List<String> searchTokenSessionId(String keyword, int start, int size, boolean sortType) {
		return stpLogic.searchTokenSessionId(keyword, start, size, sortType);
	}

	
	// ------------------- Account ban -------------------  

	/**
	 * Ban: specified account
	 * <p> This method will not directly kick this account id offline, if you need to go offline immediately after banning, please add a call to StpUtil.logout(id)
	 *
	 * @param loginId specified account id 
	 * @param time ban time, unit: seconds (-1=permanent ban)
	 */
	public static void disable(Object loginId, long time) {
		stpLogic.disable(loginId, time);
	}

	/**
	 * Determine: whether the specified account has been banned (true=has been banned, false=has not been banned) 
	 *
	 * @param loginId account id
	 * @return / 
	 */
	public static boolean isDisable(Object loginId) {
		return stpLogic.isDisable(loginId);
	}

	/**
	 * Verify: whether the specified account has been banned, if banned, throw an exception
	 *
	 * @param loginId account id
	 */
	public static void checkDisable(Object loginId) {
		stpLogic.checkDisable(loginId);
	}

	/**
	 * Get: remaining ban time for the specified account, unit: seconds (-1=permanent ban, -2=not banned)
	 *
	 * @param loginId account id
	 * @return / 
	 */
	public static long getDisableTime(Object loginId) {
		return stpLogic.getDisableTime(loginId);
	}

	/**
	 * Unban: specified account
	 *
	 * @param loginId account id
	 */
	public static void untieDisable(Object loginId) {
		stpLogic.untieDisable(loginId);
	}

	
	// ------------------- Classified ban -------------------  

	/**
	 * Ban: specified service of the specified account 
	 * <p> This method will not directly kick this account id offline, if you need to go offline immediately after banning, please add a call to StpUtil.logout(id)
	 *
	 * @param loginId specified account id
	 * @param service specified service 
	 * @param time ban time, unit: seconds (-1=permanent ban)
	 */
	public static void disable(Object loginId, String service, long time) {
		stpLogic.disable(loginId, service, time);
	}

	/**
	 * Determine: whether the specified service of the specified account has been banned (true=has been banned, false=has not been banned)
	 *
	 * @param loginId account id
	 * @param service specified service 
	 * @return / 
	 */
	public static boolean isDisable(Object loginId, String service) {
		return stpLogic.isDisable(loginId, service);
	}

	/**
	 * Verify: whether the specified account and specified service has been banned, if banned, throw an exception
	 *
	 * @param loginId account id
	 * @param services specified service, can specify multiple 
	 */
	public static void checkDisable(Object loginId, String... services) {
		stpLogic.checkDisable(loginId, services);
	}

	/**
	 * Get: remaining ban time for the specified account and specified service, unit: seconds (-1=permanent ban, -2=not banned)
	 *
	 * @param loginId account id
	 * @param service specified service 
	 * @return see note 
	 */
	public static long getDisableTime(Object loginId, String service) {
		return stpLogic.getDisableTime(loginId, service);
	}

	/**
	 * Unban: specified account and specified service
	 *
	 * @param loginId account id
	 * @param services specified service, can specify multiple 
	 */
	public static void untieDisable(Object loginId, String... services) {
		stpLogic.untieDisable(loginId, services);
	}


	// ------------------- Ladder ban -------------------  

	/**
	 * Ban: specified account, and specify ban level
	 *
	 * @param loginId specified account id 
	 * @param level specified ban level 
	 * @param time ban time, unit: seconds (-1=permanent ban)
	 */
	public static void disableLevel(Object loginId, int level, long time) {
		stpLogic.disableLevel(loginId, level, time);
	}

	/**
	 * Ban: specified service of the specified account, and specify ban level
	 *
	 * @param loginId specified account id 
	 * @param service specified ban service 
	 * @param level specified ban level 
	 * @param time ban time, unit: seconds (-1=permanent ban)
	 */
	public static void disableLevel(Object loginId, String service, int level, long time) {
		stpLogic.disableLevel(loginId, service, level, time);
	}

	/**
	 * Determine: whether the specified account has been banned to the specified level
	 *
	 * @param loginId specified account id 
	 * @param level specified ban level 
	 * @return / 
	 */
	public static boolean isDisableLevel(Object loginId, int level) {
		return stpLogic.isDisableLevel(loginId, level);
	}

	/**
	 * Determine: whether the specified service of the specified account has been banned to the specified level 
	 *
	 * @param loginId specified account id 
	 * @param service specified ban service 
	 * @param level specified ban level 
	 * @return / 
	 */
	public static boolean isDisableLevel(Object loginId, String service, int level) {
		return stpLogic.isDisableLevel(loginId, service, level);
	}

	/**
	 * Verify: whether the specified account has been banned to the specified level (if reached, throw an exception)
	 *
	 * @param loginId specified account id 
	 * @param level ban level (only when ban level ≥ this value will throw an exception)
	 */
	public static void checkDisableLevel(Object loginId, int level) {
		stpLogic.checkDisableLevel(loginId, level);
	}

	/**
	 * Verify: whether the specified service of the specified account has been banned to the specified level (if reached, throw an exception)
	 *
	 * @param loginId specified account id 
	 * @param service specified ban service 
	 * @param level ban level (only when ban level ≥ this value will throw an exception)
	 */
	public static void checkDisableLevel(Object loginId, String service, int level) {
		stpLogic.checkDisableLevel(loginId, service, level);
	}

	/**
	 * Get: the ban level of the specified account, if not banned, return -2 
	 *
	 * @param loginId specified account id 
	 * @return / 
	 */
	public static int getDisableLevel(Object loginId) {
		return stpLogic.getDisableLevel(loginId);
	}

	/**
	 * Get: the ban level of the specified service of the specified account, if not banned, return -2 
	 *
	 * @param loginId specified account id 
	 * @param service specified ban service 
	 * @return / 
	 */
	public static int getDisableLevel(Object loginId, String service) {
		return stpLogic.getDisableLevel(loginId, service);
	}
	
	
	// ------------------- Temporary identity switching -------------------

	/**
	 * Temporarily switch identity to the specified account id
	 *
	 * @param loginId specified loginId 
	 */
	public static void switchTo(Object loginId) {
		stpLogic.switchTo(loginId);
	}

	/**
	 * End temporary identity switching
	 */
	public static void endSwitch() {
		stpLogic.endSwitch();
	}

	/**
	 * Determine whether the current request is in [ temporary identity switching ]
	 *
	 * @return /
	 */
	public static boolean isSwitch() {
		return stpLogic.isSwitch();
	}

	/**
	 * In a lambda code block, temporarily switch identity to the specified account id, automatically restore after lambda ends
	 *
	 * @param loginId specified account id 
	 * @param function method to execute 
	 */
	public static void switchTo(Object loginId, SaFunction function) {
		stpLogic.switchTo(loginId, function);
	}
	

	// ------------------- Secondary authentication -------------------  

	/**
	 * Enable secondary authentication in the current session
	 *
	 * @param safeTime maintenance time (unit: seconds) 
	 */
	public static void openSafe(long safeTime) {
		stpLogic.openSafe(safeTime);
	}

	/**
	 * Enable secondary authentication in the current session
	 *
	 * @param service business identifier  
	 * @param safeTime maintenance time (unit: seconds) 
	 */
	public static void openSafe(String service, long safeTime) {
		stpLogic.openSafe(service, safeTime);
	}

	/**
	 * Determine: whether the current session is within the secondary authentication time
	 *
	 * @return true=secondary authentication passed, false=secondary authentication not performed or authentication has expired 
	 */
	public static boolean isSafe() {
		return stpLogic.isSafe();
	}

	/**
	 * Determine: whether the current session is within the secondary authentication time for the specified business
	 *
	 * @param service business identifier  
	 * @return true=secondary authentication passed, false=secondary authentication not performed or authentication has expired 
	 */
	public static boolean isSafe(String service) {
		return stpLogic.isSafe(service);
	}

	/**
	 * Determine: whether the specified token is within the secondary authentication time
	 *
	 * @param tokenValue Token value  
	 * @param service business identifier  
	 * @return true=secondary authentication passed, false=secondary authentication not performed or authentication has expired 
	 */
	public static boolean isSafe(String tokenValue, String service) {
		return stpLogic.isSafe(tokenValue, service);
	}

	/**
	 * Verify: whether the current session has passed secondary authentication, if not, throw an exception
	 */
	public static void checkSafe() {
		stpLogic.checkSafe();
	}

	/**
	 * Verify: check whether the current session has passed secondary authentication for the specified business, if not, throw an exception
	 *
	 * @param service business identifier  
	 */
	public static void checkSafe(String service) {
		stpLogic.checkSafe(service);
	}

	/**
	 * Get: remaining validity time of the current session's secondary authentication (unit: seconds, return -2 if not passed secondary authentication)
	 *
	 * @return remaining validity time
	 */
	public static long getSafeTime() {
		return stpLogic.getSafeTime();
	}

	/**
	 * Get: remaining validity time of the current session's secondary authentication (unit: seconds, return -2 if not passed secondary authentication)
	 *
	 * @param service business identifier  
	 * @return remaining validity time
	 */
	public static long getSafeTime(String service) {
		return stpLogic.getSafeTime(service);
	}

	/**
	 * End secondary authentication in the current session 
	 */
	public static void closeSafe() {
		stpLogic.closeSafe();
	}

	/**
	 * End the secondary authentication for the specified business identifier in the current session
	 *
	 * @param service business identifier  
	 */
	public static void closeSafe(String service) {
		stpLogic.closeSafe(service);
	}

}