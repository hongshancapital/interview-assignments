//
//  ButtonStyle.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct PrimaryButtonStyle: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> Body {
        Body(configuration: configuration)
    }
    
    struct Body: View {
        // tracks if the button is enabled or not
        @Environment(\.isEnabled) var isEnabled
        // tracks the pressed state
        let configuration: PrimaryButtonStyle.Configuration
        
        var body: some View {
            let isPressed = configuration.isPressed
            var fillColor = isEnabled ? Color.scAccent : Color.scDisabled
            if isEnabled && isPressed {
                fillColor = fillColor.opacity(0.618)
            }

            return
                configuration.label
                .padding(.vertical, 8)
                .foregroundColor(.white)
                .frame(minWidth: 0, maxWidth: .infinity)
                .background(
                    RoundedRectangle(cornerRadius: 5)
                        .fill(fillColor)
                )
        }
    }
}

struct DestructiveButtonStyle: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> Body {
        Body(configuration: configuration)
    }
    
    struct Body: View {
        // tracks if the button is enabled or not
        @Environment(\.isEnabled) var isEnabled
        // tracks the pressed state
        let configuration: PrimaryButtonStyle.Configuration
        
        var body: some View {
            let isPressed = configuration.isPressed
            var fillColor = isEnabled ? Color.scBackgroundDestructive : Color.scDisabled
            if isEnabled && isPressed {
                fillColor = fillColor.opacity(0.618)
            }
            
            return
                configuration.label
                .padding(.vertical, 8)
                .foregroundColor(.scDestructive)
                .frame(minWidth: 0, maxWidth: .infinity)
                .background(
                    RoundedRectangle(cornerRadius: 5)
                        .fill(fillColor)
                )
        }
    }
}
