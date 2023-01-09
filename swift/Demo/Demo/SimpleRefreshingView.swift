//
//  SimpleRefreshingView.swift
//  Demo
//
//  Created by lajsf on 2022/10/27.
//

import SwiftUI

struct SimpleRefreshingView: View {
    
    @State var nomore = false
    
    var body: some View {
        HStack{
            if nomore{
                Text("No more data.").foregroundColor(Color.gray)
            }else{
                ActivityIndicator(style: .medium)
                Text("Loading...").foregroundColor(Color.gray)
            }
           
        }
    }
}

struct SimpleRefreshingView_Previews: PreviewProvider {
    static var previews: some View {
        SimpleRefreshingView()
    }
}
