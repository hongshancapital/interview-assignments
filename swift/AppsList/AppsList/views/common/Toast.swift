//
//  Toast.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/16.
//

import SwiftUI

//struct Toast: View {
//
//    private var message: String
//
//    init(message: String) {
//        self.message = message
//    }
//
//    var body: some View {
//        VStack {
//            Text(message)
//                .foregroundColor(Color.black)
//        }
//        .padding(EdgeInsets.init(top: 20, leading: 30, bottom: 20, trailing: 30))
//        .background(Color.white)
//        .cornerRadius(8)
//    }
//
//}
//
//extension View {
//    func toast(show: Bool, message: String) -> some View {
//        ZStack {
//            self
//            if show, message.count > 0 {
//                Color.black
//                    .frame(maxWidth: .infinity, maxHeight: .infinity)
//                    .edgesIgnoringSafeArea(.all)
//                    .opacity(0.5)
//                    .zIndex(1)
//                Toast(message: message)
//                    .zIndex(2)
//            }
//        }
//    }
//}

struct Toast: ViewModifier {
    @Binding var show: Bool
    var title: String
    
    func body(content: Content) -> some View {
        GeometryReader { geo in
            ZStack(){
                content.zIndex(0).disabled(show)
                VStack {
                    HStack {
                        Text(title)
                            .padding(EdgeInsets(top: 20, leading: 30, bottom: 20, trailing: 30))
                            .multilineTextAlignment(.center)
                            .foregroundColor(Color.white)
                    }
                    .background(Color.black.opacity(0.4)).cornerRadius(5)
                    .frame(maxWidth: geo.size.width - 100)
                }
                .frame(width: geo.size.width, height: geo.size.height)
                .background(Color.clear)
                .zIndex(1)
                .opacity((show) ? 1 : 0)
                .animation(.easeInOut(duration: 0.25), value: show)
            }
            .onChange(of: show) { e in
                if(e){
                    DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                        show.toggle()
                    }
                }
            }
        }
    }
}

extension View {
    func toast(show: Binding<Bool>, title: String) -> some View {
        self.modifier(Toast(show: show, title: title))
    }
}
