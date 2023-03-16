//
//  ToastView.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/10.
//

import SwiftUI

struct ToastView: View {
    var isShow: Bool
    var message: String
    @State var startAnimate: Bool = false
    
    var body: some View {
        ZStack {
            Text(message)
                .font(Font.headline)
                .foregroundColor(.white)
                .padding()
                .background(Color.gray.opacity(0.8))
                .clipShape(RoundedRectangle(cornerRadius: 12))
        }
        .opacity(startAnimate ? 1 : 0)
        .animation(.easeOut(duration: 0.3), value: startAnimate)
        .edgesIgnoringSafeArea(.all)
        .onAppear {
            startAnimate = true
        }
    }
}

struct ToastView_Previews: PreviewProvider {
    static var previews: some View {
        ToastView(isShow: true, message: "Toast message")
    }
}

extension View {
    func toast(isShow: Bool, message: String) -> some View {
        ZStack {
            self
            if isShow && !message.isEmpty {
                ToastView(isShow: isShow, message: message)
            }
        }
    }
}
