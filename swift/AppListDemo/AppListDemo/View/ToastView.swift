//
//  ToastView.swift
//  AppListDemo
//
//  Created by HQ on 2022/8/19.
//

import SwiftUI

/// 图片的对齐方式
enum AlignmentStyle {
    case left, right, top, bottom
}

struct ToastView: ViewModifier {
    /// 是否显示
    @Binding var show: Bool
    
    /// 文本信息
    var message: String
    
    /// 图片名称（使用自定义图片时使用）
    var imageStr: String?
    
    /// 图片名称（使用系统图片时使用）
    var systemImageStr: String?
    
    /// 图片相对于文本的排版方式（默认在文本顶部）
    var alignmentStyle: AlignmentStyle = .top
    
    func body(content: Content) -> some View {
        GeometryReader { geo in
            ZStack {
                // 防止多次点击 .disabled(show)
                content.zIndex(0).disabled(show)
                
                ZStack {
                    ZStack {
                        if self.alignmentStyle == .top || self.alignmentStyle == .bottom {
                            VStack(spacing: 10) {
                                if self.alignmentStyle == .top {
                                    imageView()
                                    text()
                                } else {
                                    text()
                                    imageView()
                                }
                            }
                        } else {
                            HStack {
                                if self.alignmentStyle == .left {
                                    imageView()
                                    text()
                                } else {
                                    text()
                                    imageView()
                                }
                            }
                        }
                    }
                    .padding()
                    .background(Color.black.opacity(0.7).cornerRadius(8))
                }
                .frame(width: geo.size.width, height: geo.size.height)
                .background(Color.clear)
                .zIndex(1)
                .opacity((show) ? 1 : 0)
                .animation(.easeInOut(duration: 0.35), value: show)
            }
            .onChange(of: show) { e in
                if(e){
                    //消失
                    DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                        show.toggle()
                    }
                }
            }
        }
    }
    
    func text() -> some View {
        Text(message)
            .font(.system(size: 15))
            .lineSpacing(5)
            .foregroundColor(.white)
            .fixedSize(horizontal: false, vertical: true)
    }
    
    func imageView() -> AnyView? {
        if imageStr?.count ?? 0 > 0 {
            return AnyView(Image(imageStr!))
        }
        
        if systemImageStr?.count ?? 0 > 0 {
            return AnyView(Image(systemName: systemImageStr!)
                .foregroundColor(.white))
        }
        
        return nil
    }
}

extension View {
    func toast(show: Binding<Bool>, message: String, image: String? = nil, systemImage: String? = nil, alignment: AlignmentStyle = .top) -> some View {
        self.modifier(ToastView(show: show, message: message, imageStr: image, systemImageStr: systemImage, alignmentStyle: alignment))
    }
}

struct ToastView_Previews: PreviewProvider {
    @State static var show = true
    static var previews: some View {
        Text("哈哈")
            .frame(width: 100, height: 100, alignment: .center)
            .background(Color.blue)
            .toast(show: $show, message: "这是一个Toast视图，给你显示些什么内容好呢，哈哈哈哈哈哈哈哈哈哈哈", systemImage: "xmark")
    }
}
