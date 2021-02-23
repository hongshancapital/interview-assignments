//
//  LoginView.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct LoginView: View {
    
    @EnvironmentObject var appState: AppState
    @StateObject private var viewModel = LoginViewModel()
    
    private var loginButtonDisabled: Bool {
        if viewModel.isLoading {
            return true
        }
        return !viewModel.isValid
    }
    
    var body: some View {
        GeometryReader { geometry in
            VStack {
                TextField("Name", text: $viewModel.username)
                    .scTextFiledStyle()
                    .padding(.top, geometry.size.height * 0.08)
                SecureField("Password", text: $viewModel.password)
                    .scTextFiledStyle()
                HStack {
                    Spacer()
                    NavigationLink(
                        destination: AccountCreationView(),
                        label: {
                            Text("Create Account")
                                .foregroundColor(.scGray)
                        })
                }
                Button(action: {
                    self.endEditing()
                    self.viewModel.onLogin()
                }) {
                    Text("Login")
                }
                .buttonStyle(PrimaryButtonStyle())
                .padding(.horizontal, 20)
                .padding(.top, 44)
                .disabled(loginButtonDisabled)
                
                Spacer()
            }
            .padding(.horizontal, 30)
            .toast(isPresented: $viewModel.isLoading, dismissAfter: .infinity) {
                return
                    ToastView("Logging in...")
                        .toastViewStyle(IndefiniteProgressToastViewStyle())
            }
        }
        .onAppear(perform: viewModel.onAppear)
    }
    
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
