package com.videomania;

import vista.LoginVista;
import vista.PrincipalVista;

public class Main {

	public static void main(String[] args) {
		try {
			LoginVista window = new LoginVista();
			window.frame.setVisible(true);
//			PrincipalVista principalVista = new PrincipalVista();
//			principalVista.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
