package com.ecoit.asia.pdfsigner;

public abstract interface Signer<T extends SignContent<?>> {
	public abstract T[] sign(T... paramVarArgs) throws Exception;
	
	public abstract boolean[] verify(T... paramVarArgs);
}

/*
 * Location: C:\Users\HoangThiLanAnh\Downloads\ChuKiSo\CAAppletSimple.jar
 * Qualified Name: com.ecoit.asia.pdfsigner.Signer Java Class Version: 7 (51.0)
 * JD-Core Version: 0.7.1
 */