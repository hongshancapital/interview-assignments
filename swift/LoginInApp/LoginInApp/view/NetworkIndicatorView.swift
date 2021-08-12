//
//  NetworkIndicatorView.swift
//  LoginInApp
//
//  Created by yaojinhai on 2021/8/10.
//

import SwiftUI
import UIKit

struct NetworkIndicatorView: View {
    @Binding var run: Bool
    @Binding var title: String
    
    var body: some View {
        VStack(content: {
            IndicatorView(isRun: $run).background(Color.clear)
            Text(title).foregroundColor(Color(UIColor.secondaryLabel)).font(Font.system(size: 14)).background(Color.clear)
        }).background(Color.clear).frame(width: 100, height: 100, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/).background(Color(UIColor.secondarySystemBackground)).cornerRadius(10)
    }
    
}

struct NetworkIndicatorView_Previews: PreviewProvider {
    static var previews: some View {
        NetworkIndicatorView(run: .init(get: { () -> Bool in
            true
        }, set: { (newValue) in
            
        }), title: .init(get: { () -> String in
            "正在加载"
        }, set: { (newValue) in
            
        }))
    }
}

struct IndicatorView: UIViewRepresentable { 
    
    @Binding var isRun: Bool
    
    func makeUIView(context: Context) -> UIActivityIndicatorView {
        let indicatorView = UIActivityIndicatorView(style: .large)
        indicatorView.hidesWhenStopped = true
        indicatorView.backgroundColor = UIColor.clear
        return indicatorView
    }
    
    func updateUIView(_ uiView: UIActivityIndicatorView, context: Context) {
        if isRun {
            uiView.startAnimating()
        }else{
            uiView.stopAnimating()
        }
    }
    
}



