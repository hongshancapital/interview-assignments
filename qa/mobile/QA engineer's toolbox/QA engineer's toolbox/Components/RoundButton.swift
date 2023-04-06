//
//  Button.swift
//  QA engineer's toolbox
//
//  Created by Boxiang Yu - Ciic on 2021/1/5.
//

import SwiftUI

struct RoundButtonStyle: ButtonStyle {
    
    let backgroundColor: Color
    let foregroundColor: Color
    let roundRadius: CGFloat
    
    func makeBody(configuration: Self.Configuration) -> some View {
        let currentForegroundColor = configuration.isPressed ? foregroundColor.opacity(0.3) : foregroundColor
        return configuration.label
            .padding()
            .foregroundColor(currentForegroundColor)
            .background(configuration.isPressed ? backgroundColor.opacity(0.3) : backgroundColor)
            .cornerRadius(roundRadius)
            .overlay(
                RoundedRectangle(cornerRadius: roundRadius)
                    .stroke(currentForegroundColor, lineWidth: 1)
            )
            .padding([.top, .bottom], 10)
            .font(Font.system(size: 19, weight: .semibold))
    }
}

struct RoundButton: View {
    
    private static let buttonHorizontalMargins: CGFloat = 24
    
    var backgroundColor: Color
    var foregroundColor: Color
    var roundRadius: CGFloat
    
    private let title: String
    private let action: () -> Void
    
    
    init(title: String,
         backgroundColor: Color = Color.green,
         foregroundColor: Color = Color.white,
         roundRadius: CGFloat = 25,
         action: @escaping () -> Void) {
        self.backgroundColor = backgroundColor
        self.foregroundColor = foregroundColor
        self.title = title
        self.action = action
        self.roundRadius = roundRadius
    }
    
    var body: some View {
        HStack {
            Spacer(minLength: RoundButton.buttonHorizontalMargins)
            Button(action:self.action) {
                Text(self.title)
                    .frame(maxWidth:.infinity)
            }
            .buttonStyle(RoundButtonStyle(backgroundColor: backgroundColor,
                                          foregroundColor: foregroundColor,
                                          roundRadius: roundRadius
                                          )
                                          )
            Spacer(minLength: RoundButton.buttonHorizontalMargins)
        }
        .frame(maxWidth:.infinity)
    }
}
