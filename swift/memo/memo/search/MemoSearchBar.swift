//
//  MemoSearchBar.swift
//  memo
//
//  Created by LI on 2021/11/27.
//

import SwiftUI

struct MemoSearchBar: View {
    
    @Binding var edit: Bool
    @Binding var text: String
    
    var placeholder: String = ""
    var change: InputFieldHandler? = nil
    var submit: InputFieldHandler? = nil
    
    private let backgroundColor = Color(red: 247/255.0, green: 247/255.0, blue: 248/255.0)
    
    var body: some View {
        VStack {
            Spacer()
            ZStack(alignment: .bottom) {
                backgroundGradient()
                VStack {
                    Spacer(minLength: 12)
                    ZStack(alignment: .center) {
                        RoundedRectangle(cornerRadius: 9)
                            .foregroundColor(Color.white)
                            .padding(.trailing, 36)
                            .padding(.leading, 36)
                        
                        RoundedRectangle(cornerRadius: 9)
                            .strokeBorder(style: StrokeStyle(lineWidth: 1, dash: [3]))
                            .lineLimit(2)
                            .foregroundColor(Color.gray)
                            .padding(.trailing, 36)
                            .padding(.leading, 36)
                        
                        InputField(placeholder: "Search ...", text: $text, edit: $edit) { value in
                            submit?(value)
                        } change: { value in
                            change?(value)
                        }
                        .background(Color.white)
                        .padding(.leading, 42)
                        .padding(.trailing, 42)
                        .padding(.top, 3)
                        .padding(.bottom, 3)
                    }
                    Spacer(minLength: 3)
                }
            }
            .frame(height: 64)
        }
    }
    
    /// 渐变图层
    private func backgroundGradient() -> some View {
        VStack(spacing: 0) {
            LinearGradient(
                gradient: Gradient(
                    colors: [
                        backgroundColor,
                        backgroundColor.opacity(0.1)
                    ]
                ),
                startPoint: .bottom,
                endPoint: .top
            )
            .frame(height: 24)
            Rectangle()
                .foregroundColor(backgroundColor)
        }
        .ignoresSafeArea()
    }
}

struct MemoSearchBar_Previews: PreviewProvider {
    @State static var edit: Bool = false
    @State static var text: String = ""
    
    static var previews: some View {
        MemoSearchBar(edit: $edit, text: $text)
    }
}
