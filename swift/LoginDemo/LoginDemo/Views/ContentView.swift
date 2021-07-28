//
//  ContentView.swift
//  LoginDemo
//
//  Created by 吕博 on 2021/7/27.
//

import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject var AccountVM: AccountViewModel
    @State var show: Bool = false
    
    var body: some View {
        VStack {
            
            ZStack {
                Color.green
                    .frame(
                        width: UIScreen.main.bounds.width,
                        height: self.show ? UIScreen.main.bounds.height / 4.5 :
                            UIScreen.main.bounds.height)
                    .clipShape(RoundedRectangle(cornerRadius: 30))
                
                VStack {
                    Image(systemName: AccountVM.netService.userInfo.avator)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 80, height: 80)
                        .foregroundColor(.white)
                    Text(AccountVM.netService.userInfo.name)
                        .foregroundColor(.white)
                }
                .padding(.top, 40)
            }
            .edgesIgnoringSafeArea(.top)

            Spacer()
            LogoutButtonPart()
        }
        .onAppear {
            withAnimation(.spring(response: 1.0, dampingFraction: 0.6, blendDuration: 0)) {
                self.show.toggle()
            }
        }
        .navigationBarHidden(true)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(AccountViewModel(style: .Login))
    }
}
