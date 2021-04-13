//
//  UserView.swift
//  LoginDemo
//
//  Created by MC on 2021/4/12.
//

import SwiftUI

/// 简单一点，只考虑了登录的样式
class UserViewModel: ObservableObject {
    @Published var username = ""
    @Published var isLogin = false
    
    init() {
        username = UserDefaults.standard.string(forKey: "username") ?? ""
        isLogin = UserDefaults.standard.bool(forKey: "isLogin")
    }
}

struct UserView: View {
    @StateObject var viewModel = UserViewModel()
    
    var body: some View {
        ZStack(alignment: .top) {
            Color.white
            
            UserHeaderView(username: viewModel.username)
            
            GeometryReader { reader in
                VStack(alignment: .center) {
                    Spacer()
                    
                    Button("Logout") {
                        
                    }
                    .foregroundColor(.red)
                    .frame(width: reader.size.width * 0.8, height: 40)
                    .background(Color("gray_1"))
                    .cornerRadius(5)
                    .padding(.bottom, 50)
                }
                .frame(width: reader.size.width)
            }
        }
        .navigationBarHidden(true)
        .ignoresSafeArea()
    }
}

struct UserHeaderView: View {
    let username: String
    
    var body: some View {
        ZStack {
            RoundedCornersView(color: .green, bottomLeading: 30, bottomTrailing: 30)
            
            VStack {
                Spacer()
                
                Text(username.isEmpty ? "" : String(username.first!))
                    .foregroundColor(.secondary)
                    .frame(width: 80, height: 80)
                    .background(
                        Color("gray_1")
                    )
                    .clipShape(Circle())
                    
                
                Text(username)
                    .foregroundColor(.white)
                    .font(.headline)
                    .frame(height: 60)
            }
        }
        .frame(height: 200)
    }
}


struct UserView_Previews: PreviewProvider {
    static var previews: some View {
        UserView()
    }
}
