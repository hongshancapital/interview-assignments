//
//  AlertMessageView.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/19.
//

import Foundation
import SwiftUI


struct AlertMessageView: View {
    
    @Binding var error: NSError?
    let time: Int
    
    var body: some View {
        Group { 
            if let error = error {
                createErrorLabel(error: error)
            }else {
                EmptyView()
            }
            
        }
    }
    
   private func createErrorLabel(error: NSError) -> some View {
        DispatchQueue.main.asyncAfter(deadline: .now() + Double(time)) { 
            self.error = nil
        }
        let messge = error.localizedFailureReason ?? error.localizedDescription
        
        return Text(messge)
            .font(Font.system(size: 14))
            .foregroundColor(Color(uiColor: .label))
            .padding(10)
            .background(Color(uiColor: .tertiarySystemBackground))
            .cornerRadius(4)
            .frame(alignment: .center)
        
    }
    
    
}
