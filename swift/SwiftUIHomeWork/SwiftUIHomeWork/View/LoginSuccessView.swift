//
//  LoginSuccessView.swift
//  SwiftUIHomeWork
//
//  Created by RemiliaScarlet on 2021/3/15.
//

import SwiftUI

struct LoginSuccessView: View {
    @State var isLogout = false
    @EnvironmentObject var userInfo:MockUserInfo
    
    var body: some View {
        VStack{
            ZStack {
                CustomRectangle()
                VStack {
                    Image("img")
                        .resizable()
                        .frame(width: 100, height: 100)
                        .clipShape(Circle())
                    
                    Text("\(userInfo.mockUserName)")
                        .foregroundColor(.white)
                }
            }
            Spacer()
            NavigationLink(
                destination: LoginView(),
                isActive: $isLogout,
                label: {
                    Button(action: {
                        self.isLogout.toggle()
                    }) {
                        ZStack{
                            Rectangle()
                                .frame(width: UIScreen.main.bounds.width/1.3, height: UIScreen.main.bounds.height/15)
                                .foregroundColor(Color("grey5"))
                                .cornerRadius(10)
                            Text("Logout")
                                .foregroundColor(.red)
                        }
                    }
                })
                .padding(.bottom, UIScreen.main.bounds.height/10)
        }
        .navigationBarHidden(true)
        .ignoresSafeArea()
    }
}

struct CustomRectangle:View {
    var body: some View{
        ZStack {
            VStack {
                Rectangle()
                    .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/5)
                    .foregroundColor(.green)
                Spacer()
            }
            VStack {
                Rectangle()
                    .frame(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height/3)
                    .foregroundColor(.green)
                    .cornerRadius(20)
            }//半圆角矩形完成。。。。。。
        }
        .frame(height:UIScreen.main.bounds.height/3)
    }
}


struct LoginSuccessView_Previews: PreviewProvider {
    static var previews: some View {
        LoginSuccessView()
    }
}
