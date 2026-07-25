//
//  HSSearchBar.swift
//  HSSearchList
//
//  Created by dongxia zhu on 2021/10/8.
//

import SwiftUI
import UIKit


struct HSSearchBar: UIViewRepresentable {
    @Binding var text: String
    
    class Cordinator: NSObject, UISearchBarDelegate {
        @Binding var text: String
        
        init(text: Binding<String>) {
            _text = text
        }
        
        func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
            text = searchText
        }
    }
    
    func makeCoordinator() -> HSSearchBar.Cordinator {
        return Cordinator(text: $text)
    }
    
    func makeUIView(context: UIViewRepresentableContext<HSSearchBar>) -> UISearchBar {
        let searchBar = UISearchBar()
        searchBar.delegate = context.coordinator
        searchBar.placeholder = "Tap here to search"
        searchBar.searchBarStyle = .minimal
        searchBar.backgroundColor = .clear
        UISearchBar.appearance().setImage(UIImage(named: "mySearchClearBtn"), for: .clear, state: .normal)
        UISearchBar.appearance().setImage(UIImage(named: "mySearchClearBtn"), for: .clear, state: .highlighted)
        
        return searchBar
    }
    
    func updateUIView(_ uiView: UISearchBar, context: UIViewRepresentableContext<HSSearchBar>) {
        uiView.text = text
        if text.count != 0 {
            uiView.setImage(UIImage(), for: .search, state: .normal)
        } else {
            uiView.setImage(UIImage(named: "mySearchIcon"), for: .search, state: .normal)
        }
    }   
}
