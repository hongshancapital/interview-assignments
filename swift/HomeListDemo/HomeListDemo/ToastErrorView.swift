//
//  ToastErrorView.swift
//  HomeListDemo
//
//  Created by yaojinhai on 2022/9/24.
//

import SwiftUI

struct ToastErrorView: View {
    
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
    
    func createErrorLabel(error: NSError) -> some View {
        DispatchQueue.main.asyncAfter(deadline: .now() + Double(time)) { 
            self.error = nil
        }
        return Text(error.localizedFailureReason ?? error.localizedDescription).font(Font.system(size: 14)).foregroundColor(Color(uiColor: .label)).padding(10).background(Color(uiColor: .tertiarySystemBackground)).cornerRadius(4).frame(alignment: .center)

    }
    
    
}

struct ToastErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ToastErrorView(error: .init(get: { 
            nil
        }, set: { error in
            
        }), time: 0)
    }
}
