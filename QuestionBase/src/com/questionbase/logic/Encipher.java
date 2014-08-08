package com.questionbase.logic;

public class Encipher {
	private static final String m_strKey1 = "zxcvbnm,./asdfg";
	private static final String m_strKey2 = "hjkl;'qwertyuiop";
	private static final String m_strKey3 = "[]\\1234567890-";
	private static final String m_strKey4 = "=` ZXCVBNM<>?:LKJ";
	private static final String m_strKey5 = "HGFDSAQWERTYUI";
	private static final String m_strKey6 = "OP{}|+_)(*&^%$#@!~";

	public Encipher() {
	}

	public static String EncodePasswd(String strPasswd) {
		String strEncodePasswd = "";
		String des = new String();
		String strKey = new String();
		if ((strPasswd == null) || (strPasswd.length() == 0)) {
			strEncodePasswd = "";
			return strEncodePasswd;
		}
		strKey = m_strKey1 + m_strKey2 + m_strKey3 + m_strKey4 + m_strKey5
				+ m_strKey6;
		for (; strPasswd.length() < 8; strPasswd = strPasswd + '\001')
			;
		des = "";
		for (int n = 0; n <= strPasswd.length() - 1; n++) {
			char code;
			char mid;
			do {
				for (code = (char) (int) Math.rint(Math.random() * 100D); code > 0
						&& ((code ^ strPasswd.charAt(n)) < 0 || (code ^ strPasswd
								.charAt(n)) > 90); code--)
					;
				mid = '\0';
				int flag = code ^ strPasswd.charAt(n);
				if (flag > 93)
					mid = '\0';
				else
					mid = strKey.charAt(flag);
			} while (!((code > '#') & (code < 'z') & (code != '|')
					& (code != '\'') & (code != ',') & (mid != '|')
					& (mid != '\'') & (mid != ',')));
			char temp = '\0';
			temp = strKey.charAt(code ^ strPasswd.charAt(n));
			des = des + code + temp;
		}

		strEncodePasswd = des;
		return strEncodePasswd;
	}

	public static String DecodePasswd(String varCode) {
		String des = "";
		String strKey = "";
		if (varCode == null || varCode.length() == 0)
			return "";
		strKey = m_strKey1 + m_strKey2 + m_strKey3 + m_strKey4 + m_strKey5
				+ m_strKey6;
		if (varCode.length() % 2 == 1)
			varCode = varCode + "?";
		des = "";
		int n;
		for (n = 0; n <= varCode.length() / 2 - 1; n++) {
			char b = varCode.charAt(n * 2);
			int a = strKey.indexOf(varCode.charAt(n * 2 + 1));
			des = des + (char) (b ^ a);
		}

		n = des.indexOf('\001');
		if (n > 0)
			return des.substring(0, n);
		else
			return des;
	}
}