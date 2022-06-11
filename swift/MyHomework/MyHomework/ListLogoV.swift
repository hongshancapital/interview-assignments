//
//  ListLogoV.swift
//  MyHomework
//
//  Created by mao on 2022/6/10.
//

import SwiftUI

struct ListLogoV: View {
    
    var image: Image?
    
    var body: some View {
        image
            .clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
            .overlay {
                Circle().stroke(.white, lineWidth: 4)
            }
            .shadow(radius: 7)
        
    }
}

struct ListLogoV_Previews: PreviewProvider {
    static var previews: some View {
        ListLogoV()
    }
}
