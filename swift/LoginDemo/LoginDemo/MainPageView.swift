//
//  MainPageView.swift
//  LoginDemo
//
//  Created by 高丽军 on 2021/9/13.
//

import SwiftUI

struct MainPageView:View {
    @Binding var isShow: Bool
    @Binding var loginName: String
    var body: some View {
        NavigationView {
            HStack {
                Button(action: {
                    isShow = false
                }, label: {
                    Text("Logout").foregroundColor(.red)
                })
                .frame(height: 50, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .padding([.leading,.trailing],130)
                .background(Color(grayColor))
                .cornerRadius(10.0)
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}
