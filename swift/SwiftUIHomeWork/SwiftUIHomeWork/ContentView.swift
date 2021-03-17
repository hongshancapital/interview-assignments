//
//  ContentView.swift
//  SwiftUIHomeWork
//
//  Created by RemiliaScarlet on 2021/3/15.
//

import SwiftUI

struct ContentView: View {
    //@EnvironmentObject var userInfo:MockUserInfo
    var body: some View {
        LoginView()
            .environmentObject(MockUserInfo())
    }
    
}

struct UnderLine:View {
    var body: some View{
        Rectangle()
            .frame(height: 2.0, alignment: .bottom)
            .foregroundColor(Color.gray)
    }
}//定义了一个下划线

struct ConfirmButton:View {
    var body: some View{
        Rectangle()
            .frame(width: UIScreen.main.bounds.width/1.3, height: UIScreen.main.bounds.height/15)
            .foregroundColor(.green)
            .cornerRadius(10)
    }
}//定义了按钮样式

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
