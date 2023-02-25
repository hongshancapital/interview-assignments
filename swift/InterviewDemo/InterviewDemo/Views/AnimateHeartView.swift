//
//  AnimateHeartView.swift
//  InterviewDemo
//
//  Created by Peng Shu on 2023/2/24.
//

import SwiftUI

struct AnimateHeartView: View {
    @State var animate = false
    
    var body: some View {
        Image(systemName: "heart.fill")
            .foregroundColor(.red)
            .scaleEffect(animate ? 1.2 : 1)
            .animation(.spring(), value: animate)
            .onAppear {
                animate = true
            }
    }
}
