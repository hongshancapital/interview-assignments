//
//  BlurView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: -桥接UIKit View实现毛玻璃模糊效果
struct BlurView: UIViewRepresentable {
    //MARK: - 属性
    let style: UIBlurEffect.Style
    
    //MARK: - 实现方法
    func makeUIView(context: UIViewRepresentableContext<BlurView>) -> UIView {
        let view = UIView(frame: .zero)
        view.backgroundColor = .clear
        
        let blurEffect = UIBlurEffect(style: style)
        let blueView = UIVisualEffectView(effect: blurEffect)
        
        blueView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(blueView)
        NSLayoutConstraint.activate([
            blueView.heightAnchor.constraint(equalTo: view.heightAnchor),
            blueView.widthAnchor.constraint(equalTo: view.widthAnchor)
        ])
        return view
    }
    
    func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<BlurView>) {
        //
    }
}

//MARK: - Preview
struct BlurView_Previews: PreviewProvider {
    static var previews: some View {
        BlurView(style: .dark)
    }
}
