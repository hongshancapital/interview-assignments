//
//  HomeView.swift
//  Login
//
//  Created by xiwang wang on 2021/8/31.
//

import SwiftUI

struct HomeView: View {
    @Environment(\.presentationMode) var presentationMode
    var body: some View {
        VStack(alignment:.leading, spacing: 0){
            Rectangle()
                .fill(Color("themeGreen"))
                .frame(height:240,alignment: .top)
                .overlay(
                    VStack{
                        Circle()
                            .fill( Color("lightGray"))
                            .frame(height: 66, alignment: .bottom)
                            .overlay(
                                Text("S").font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                            )
                            .cornerRadius(33)
                            .padding()
                        Text("Simon")
                    }
                )
                .cornerRadius(24)
            Spacer()
            Button(action: {
                presentationMode.wrappedValue.dismiss()
            }, label: {
                Rectangle()
                    .fill( Color("lightGray"))
                    .frame(height: 44, alignment: .center)
                    .overlay(Text("logout").foregroundColor(.red)
                    )
                    .cornerRadius(8)
                    .padding(EdgeInsets(top: 25, leading: 30, bottom: 25, trailing: 30))
            })
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
